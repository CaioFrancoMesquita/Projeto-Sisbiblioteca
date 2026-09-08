package com.lab.jpa.sisbiblioteca.repository;

import com.lab.jpa.sisbiblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorId(Long autorId);

    @Query("select l from Livro l join fetch l.autor")
    List<Livro> findAllWithAutor();

    @Query("select l from Livro l join fetch l.autor where l.id = :id")
    Optional<Livro> findByIdWithAutor(@Param("id") Long id);
}
