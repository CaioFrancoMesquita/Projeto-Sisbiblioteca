package com.lab.jpa.sisbiblioteca.repository;

import com.lab.jpa.sisbiblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("select distinct a from Autor a left join fetch a.livros")
    List<Autor> findAllWithLivros();

    @Query("select distinct a from Autor a left join fetch a.livros where a.id = :id")
    Optional<Autor> findByIdWithLivros(@Param("id") Long id);
}