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

    @Query("")
    List<Autor> findAllWithLivros();

    @Query("")
    Optional<Autor> findByIdWithLivros(@Param("id") Long id);
}
