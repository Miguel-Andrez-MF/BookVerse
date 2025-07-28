package com.alura.bookverse.model;

import com.alura.bookverse.dto.LibroDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String titulo;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Idioma> idiomas = new ArrayList<>();

    private double numeroDescargas;

    // Constructor para convertir desde LibroDTO
    public Libro(LibroDTO libroDTO) {
        this.titulo = libroDTO.titulo();
        this.idiomas = libroDTO.idiomas().stream()
                .map(Idioma::fromCategoriaAPI)
                .collect(Collectors.toList());
        this.numeroDescargas = libroDTO.numeroDescarga();

        // Convertir la lista de AutorDTO a lista de Autor
        this.autores = libroDTO.autores().stream()
                .map(autorDTO -> new Autor(
                        autorDTO.nombre(),
                        autorDTO.fechaNacimiento(),
                        autorDTO.fechaFallecimiento()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo + "\n" +
                "Autores: " + autores + "\n" +
                "Idioma: " + idiomas + "\n" +
                "Numero de descargas: " + numeroDescargas;
    }
}
