package com.neeol.alunobackend.controller;


import com.neeol.alunobackend.model.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class CursoController {

    private List<Curso> cursos = Arrays.asList(new Curso(1, "Java"),
            new Curso(2, "Angular"),
            new Curso(3, "JPA"));


    @GetMapping("cursos")
    public List<Curso> getCursos() {

        return cursos;
    }

    @GetMapping("cursos/{id}")
    public ResponseEntity<Curso> getCurso(@PathVariable int id){

        Curso curso = cursos.stream().filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso nao encontrado"));

        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

}
