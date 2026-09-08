package com.lab.jpa.sisbiblioteca.service;

import com.lab.jpa.sisbiblioteca.model.Autor;
import com.lab.jpa.sisbiblioteca.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Transactional(readOnly = true)
    public List<Autor> listar() {
        return autorRepository.findAllWithLivros();
    }

    @Transactional(readOnly = true)
    public Autor buscar(Long id) {
        return autorRepository.findByIdWithLivros(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado: " + id));
    }

    @Transactional
    public Autor criar(String nome) {
        return autorRepository.save(new Autor(nome));
    }

    @Transactional
    public Autor atualizar(Long id, String nome) {
        Autor autor = buscar(id);
        autor.setNome(nome);
        return autor;
    }

    @Transactional
    public void remover(Long id) {
        autorRepository.delete(buscar(id));
    }
}
