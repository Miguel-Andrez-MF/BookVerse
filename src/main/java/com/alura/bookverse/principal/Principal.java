package com.alura.bookverse.principal;

import com.alura.bookverse.dto.DatosDTO;
import com.alura.bookverse.dto.LibroDTO;
import com.alura.bookverse.model.Autor;
import com.alura.bookverse.model.Idioma;
import com.alura.bookverse.model.Libro;
import com.alura.bookverse.repository.AutorRepository;
import com.alura.bookverse.repository.LibroReposritory;
import com.alura.bookverse.service.ConsumoAPI;
import com.alura.bookverse.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final String URL_API = "https://gutendex.com/books/?search=";

    private LibroReposritory libroRepository;
    private AutorRepository autorRepository;
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private boolean ejecutando = true;

    public Principal(LibroReposritory libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        mostrarBanner();

        while (ejecutando) {
            mostrarOpciones();
            procesarOpcion();
        }

        System.out.println("\n¡Gracias por usar BookVerse!");
        System.out.println("Hasta la próxima...");
    }

    private void mostrarBanner() {
        System.out.println("""
                ============================================================
                                       BookVerse API
                                Sistema de Gestión de Libros
                ============================================================""");
    }

    private void mostrarOpciones() {
        System.out.println("MENÚ PRINCIPAL:");
        System.out.println("  1) Buscar libro por su título");
        System.out.println("  2) Mostrar libros registrados");
        System.out.println("  3) Mostrar autores registrados");
        System.out.println("  4) Buscar autores por año");
        System.out.println("  5) Mostrar libros por idioma");
        System.out.println("  0) Salir");
        System.out.println("─".repeat(60));
        System.out.print("Seleccione una opción: ");
    }

    private void procesarOpcion() {
        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarTodoslosLibros();
                    break;
                case 3:
                    mostrarTodoslosAutores();
                    break;
                case 4:
                    mostrarAutoresVivosAnio();
                    break;
                case 5:
                    mostrarLibrosIdioma();
                    break;
                case 0:
                    ejecutando = false;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese un número del 0 al 5.");
            }

            if (ejecutando) {
                System.out.println("\nPresione ENTER para continuar...");
                teclado.nextLine();
            }

        } catch (Exception e) {
            System.out.println("Error: Por favor ingrese un número válido.");
            teclado.nextLine();
        }
    }

    public void buscarLibroPorTitulo() {
        System.out.println("Ingrese el título del libro que desea buscar:");
        String titulo = teclado.nextLine();

        List<Libro> librosSimilares = libroRepository.findByTituloContainingIgnoreCase(titulo);
        
        if (!librosSimilares.isEmpty()) {
            mostrarLibrosSimilares(librosSimilares, titulo);

            if (preguntarSiEsElBuscado()) {
                return;
            }
        }
        buscarEnAPI(titulo, librosSimilares);
    }
    
    private void mostrarLibrosSimilares(List<Libro> libros, String terminoBusqueda) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("HAY ALGUNOS LIBROS YA REGISTRADOS SIMILARES A TU BÚSQUEDA");
        System.out.println("   Término buscado: \"" + terminoBusqueda + "\"");
        System.out.println("=".repeat(60));
        
        for (int i = 0; i < libros.size(); i++) {
            System.out.println("\n--- Libro " + (i + 1) + " ---");
            System.out.println("Título: " + libros.get(i).getTitulo());
            System.out.println("Autores: " + libros.get(i).getAutores().stream()
                    .map(autor -> autor.getNombre())
                    .collect(Collectors.joining(", ")));
            System.out.println("Idiomas: " + libros.get(i).getIdiomas());
            System.out.println("Descargas: " + libros.get(i).getNumeroDescargas());
        }
        System.out.println("\n" + "-".repeat(60));
    }
    
    private boolean preguntarSiEsElBuscado() {
        System.out.println("\n¿Alguno de los libros mostrados es el que buscas?");
        System.out.println("1) Sí, encontré el libro que buscaba");
        System.out.println("2) No, ninguno de los registrados es el que busco");
        System.out.print("Selecciona una opción (1 o 2): ");
        
        try {
            int respuesta = Integer.parseInt(teclado.nextLine());
            if (respuesta == 1) {
                System.out.println("\n¡Genial! El libro ya estaba en tu biblioteca.");
                return true;
            } else if (respuesta == 2) {
                System.out.println("\nEntendido. Buscaré en la API externa...");
                return false;
            } else {
                System.out.println("Opción no válida. Buscaré en la API externa...");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Buscaré en la API externa...");
            return false;
        }
    }
    
    private void buscarEnAPI(String titulo, List<Libro> librosExistentes) {
        try {
            var json = consumoApi.obtenerDatos(URL_API + titulo.replace(" ", "%20"));
            var resultado = conversor.obtenerDatos(json, DatosDTO.class);
            
            if (resultado.libros() == null || resultado.libros().isEmpty()) {
                System.out.println("\nNo se encontraron libros en la API para: \"" + titulo + "\"");
                return;
            }

            List<String> titulosExistentes = librosExistentes.stream()
                    .map(libro -> libro.getTitulo().toLowerCase().trim())
                    .collect(Collectors.toList());

            Optional<LibroDTO> libroNuevo = resultado.libros().stream()
                    .filter(libroDTO -> !titulosExistentes.contains(libroDTO.titulo().toLowerCase().trim()))
                    .findFirst();
            
            if (libroNuevo.isPresent()) {
                Libro libroEntity = new Libro(libroNuevo.get());
                
                System.out.println("\n" + "=".repeat(60));
                System.out.println(" LIBRO ENCONTRADO EN LA API");
                System.out.println("=".repeat(60));
                System.out.println("Título: " + libroEntity.getTitulo());
                System.out.println("Autores: " + libroEntity.getAutores().stream()
                        .map(autor -> autor.getNombre())
                        .collect(Collectors.joining(", ")));
                System.out.println("Idiomas: " + libroEntity.getIdiomas());
                System.out.println("Descargas: " + libroEntity.getNumeroDescargas());
                
                if (confirmarGuardado()) {
                    libroRepository.save(libroEntity);
                    System.out.println("\n¡Libro guardado exitosamente en la biblioteca!");
                } else {
                    System.out.println("\nLibro no guardado.");
                }
                
            } else {
                System.out.println("\nLos libros encontrados en la API ya están registrados en la biblioteca.");
            }
            
        } catch (Exception e) {
            System.out.println("\nError al buscar en la API: " + e.getMessage());
        }
    }
    
    private boolean confirmarGuardado() {
        System.out.println("\n¿Deseas guardar este libro en tu biblioteca? (s/n): ");
        String respuesta = teclado.nextLine().toLowerCase().trim();
        return respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
    }

    public void mostrarTodoslosLibros() {
        System.out.println("Mostrando todos los libros registrados:");
        List<Libro> libros = (List<Libro>) libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la biblioteca.");
        } else {
            for (int i = 0; i < libros.size(); i++) {
                System.out.println("\n------ Libro " + (i + 1) + " ------");
                System.out.println("Título: " + libros.get(i).getTitulo());
                System.out.println("Autores: " + libros.get(i).getAutores().stream()
                        .map(autor -> autor.getNombre())
                        .collect(Collectors.joining(", ")));
                System.out.println("Idiomas: " + libros.get(i).getIdiomas());
                System.out.println("Descargas: " + libros.get(i).getNumeroDescargas());
            }
            System.out.println("\n" + "-".repeat(60));
        }
    }

    public void mostrarTodoslosAutores() {
        System.out.println("Mostrando todos los Autores registrados:");
        List<Autor> autores = (List<Autor>) autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay Autores registrados en la biblioteca.");
        } else {
            for (int i = 0; i < autores.size(); i++) {
                System.out.println("\n------ Autor " + (i + 1) + " ------");
                System.out.println("Nombre: " + autores.get(i).getNombre());
                System.out.println("Fecha Nacimiento: " + autores.get(i).getFechaNacimiento());
                System.out.println("Fecha Fallecimiento: " + autores.get(i).getFechaFallecimiento());
            }
            System.out.println("\n" + "-".repeat(60));
        }
    }

    public void mostrarAutoresVivosAnio() {
        System.out.println("Ingrese el año para buscar Autores vivos:");
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No hay autores vivos registrados en el año " + anio);
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            for (int i = 0; i < autoresVivos.size(); i++) {
                System.out.println("\n------ Autor " + (i + 1) + " ------");
                System.out.println("Nombre: " + autoresVivos.get(i).getNombre());
                System.out.println("Fecha Nacimiento: " + autoresVivos.get(i).getFechaNacimiento());
                System.out.println("Fecha Fallecimiento: " + autoresVivos.get(i).getFechaFallecimiento());
            }
            System.out.println("\n" + "-".repeat(60));
        }
    }

    public void mostrarLibrosIdioma() {
        System.out.println("Ingrese el Idioma para buscar los libros:");
        System.out.println("Opciones disponibles:");
        System.out.println("- en (Inglés)");
        System.out.println("- es (Español)");
        System.out.println("- fr (Francés)");
        System.out.println("- de (Alemán)");
        System.out.println("- pt (Portugués)");

        String idioma = teclado.nextLine().trim().toLowerCase();

        try {
            List<Libro> librosPorIdioma = libroRepository.findByIdioma(Idioma.fromCategoriaAPI(idioma));

            if (librosPorIdioma.isEmpty()) {
                System.out.println("No hay libros registrados en la biblioteca de ese Idioma.");
            } else {
                System.out.println(librosPorIdioma.size() + " Libros encontrados en idioma: " + Idioma.fromCategoriaAPI(idioma));
                for (int i = 0; i < librosPorIdioma.size(); i++) {
                    System.out.println("\n------ Libro " + (i + 1) + " ------");
                    System.out.println("Título: " + librosPorIdioma.get(i).getTitulo());
                    System.out.println("Autores: " + librosPorIdioma.get(i).getAutores().stream()
                            .map(autor -> autor.getNombre())
                            .collect(Collectors.joining(", ")));
                    System.out.println("Idiomas: " + librosPorIdioma.get(i).getIdiomas());
                    System.out.println("Descargas: " + librosPorIdioma.get(i).getNumeroDescargas());
                }
                System.out.println("\n" + "-".repeat(60));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Por favor, ingresa un código de idioma válido (ej: 'en' para inglés, 'es' para español)");
        }
    }
}