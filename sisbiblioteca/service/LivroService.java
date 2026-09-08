package com.lab.jpa.sisbiblioteca.service;

import com.lab.jpa.sisbiblioteca.model.Autor;
import com.lab.jpa.sisbiblioteca.model.Livro;
import com.lab.jpa.sisbiblioteca.repository.AutorRepository;
import com.lab.jpa.sisbiblioteca.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional(readOnly = true)
    public List<Livro> listar() {
        return livroRepository.findAllWithAutor();
    }

    @Transactional(readOnly = true)
    public Livro buscar(Long id) {
        return livroRepository.findByIdWithAutor(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado: " + id));
    }

    @Transactional
    public Livro criar(String titulo, Integer anoPublicacao, Long autorId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado: " + autorId));
        Livro livro = new Livro(titulo, anoPublicacao, autor);
        autor.adicionarLivro(livro);
        return livroRepository.save(livro);
    }

    @Transactional
    public Livro atualizar(Long id, String titulo, Integer anoPublicacao, Long autorId) {
        Livro livro = buscar(id);
        Autor novoAutor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado: " + autorId));
        if (livro.getAutor() != novoAutor) {
            livro.getAutor().removerLivro(livro);
            novoAutor.adicionarLivro(livro);
        }
        livro.setTitulo(titulo);
        livro.setAnoPublicacao(anoPublicacao);
        return livro;
    }

    @Transactional
    public void remover(Long id) {
        Livro livro = buscar(id);
        livro.getAutor().removerLivro(livro);
    }
}
