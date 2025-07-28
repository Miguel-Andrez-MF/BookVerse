package com.alura.bookverse.repository;

import com.alura.bookverse.model.Idioma;
import com.alura.bookverse.model.Libro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LibroReposritory extends CrudRepository<Libro, Long> {

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> findByIdioma(Idioma idioma);

}
