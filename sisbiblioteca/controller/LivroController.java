package com.lab.jpa.sisbiblioteca.controller;

import com.lab.jpa.sisbiblioteca.model.Livro;
import com.lab.jpa.sisbiblioteca.service.LivroService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> listar() {
        return livroService.listar();
    }

    @GetMapping("/{id}")
    public Livro buscar(@PathVariable Long id) {
        return executar(() -> livroService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro criar(@Valid @RequestBody LivroRequest request) {
        return executar(() -> livroService.criar(request.titulo(), request.anoPublicacao(), request.autorId()));
    }

    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        return executar(() -> livroService.atualizar(id, request.titulo(), request.anoPublicacao(), request.autorId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        executar(() -> {
            livroService.remover(id);
            return null;
        });
    }

    private <T> T executar(java.util.function.Supplier<T> operacao) {
        try {
            return operacao.get();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    public record LivroRequest(
            @NotBlank String titulo,
            @NotNull Integer anoPublicacao,
            @NotNull Long autorId) {
    }
}
