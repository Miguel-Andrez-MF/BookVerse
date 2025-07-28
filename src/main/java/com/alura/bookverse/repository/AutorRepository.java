package com.alura.bookverse.repository;

import com.alura.bookverse.model.Autor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AutorRepository extends CrudRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :anio)")
    List<Autor> findAutoresVivosEnAnio(int anio);
}
