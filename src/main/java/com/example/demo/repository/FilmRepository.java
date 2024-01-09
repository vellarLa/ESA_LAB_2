package com.example.demo.repository;

import com.example.demo.data.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
    Film findFirstById(Long id);
    @Override
    List<Film> findAll();
}
