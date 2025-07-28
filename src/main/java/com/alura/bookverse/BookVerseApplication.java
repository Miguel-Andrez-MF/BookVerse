package com.alura.bookverse;

import com.alura.bookverse.principal.Principal;
import com.alura.bookverse.repository.AutorRepository;
import com.alura.bookverse.repository.LibroReposritory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class BookVerseApplication implements CommandLineRunner {

    @Autowired
    private LibroReposritory libroReposritory;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookVerseApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(libroReposritory, autorRepository);
        principal.muestraElMenu();
    }
}
