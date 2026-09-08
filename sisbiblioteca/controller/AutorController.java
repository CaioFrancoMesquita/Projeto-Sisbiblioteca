package com.lab.jpa.sisbiblioteca.controller;

import com.lab.jpa.sisbiblioteca.model.Autor;
import com.lab.jpa.sisbiblioteca.service.AutorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<Autor> listar() {
        return autorService.listar();
    }

    @GetMapping("/{id}")
    public Autor buscar(@PathVariable Long id) {
        return executar(() -> autorService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autor criar(@Valid @RequestBody AutorRequest request) {
        return autorService.criar(request.nome());
    }

    @PutMapping("/{id}")
    public Autor atualizar(@PathVariable Long id, @Valid @RequestBody AutorRequest request) {
        return executar(() -> autorService.atualizar(id, request.nome()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        executar(() -> {
            autorService.remover(id);
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

    public record AutorRequest(@NotBlank String nome) {
    }
}
