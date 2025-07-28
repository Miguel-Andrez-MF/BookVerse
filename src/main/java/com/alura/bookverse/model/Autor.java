package com.alura.bookverse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer fechaNacimiento;

    private Integer fechaFallecimiento;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Libro> libros;

    // Constructor para convertir desde AutorDTO
    public Autor(String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
        this.libros = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombre + " (" + fechaNacimiento + " - " + fechaFallecimiento + ")";
    }
}
