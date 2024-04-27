package com.neeol.alunobackend.controller;


import com.neeol.alunobackend.model.Aluno;
import com.neeol.alunobackend.model.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class AlunoController {

    private List<Curso> cursos = Arrays.asList(new Curso(1, "Java"),
            new Curso(2, "Angular"),
            new Curso(3, "JPA"));

    private List<Aluno> alunos = new ArrayList<Aluno>();


    @GetMapping("alunos")
    public List<Aluno> getAlunos() {

        return alunos;
    };

    @GetMapping("alunos/{id}")
    public ResponseEntity<Aluno> getAluno(@PathVariable int id){

        Aluno aluno = alunos.stream().filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        return ResponseEntity.ok(aluno);

    }


    @PostMapping("alunos")
    public ResponseEntity<Aluno> addAluno(@RequestBody Aluno aluno){

        System.out.println(aluno.toString());

        aluno.setId(alunos.size() + 1);
        aluno.setCurso(cursos.get(Integer.parseInt(aluno.getCurso().getNome()) - 1));

        alunos.add(aluno);

        //Cria a url do produto que está sendo criado. O response "created" exige que passe o URI do dado criado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aluno.getId())
                .toUri();

        System.out.println(aluno.toString());

        return ResponseEntity.created(location).body(aluno);
    }

}
