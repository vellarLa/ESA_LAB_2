package com.example.demo.service;

import com.example.demo.dto.FilmDto;

import java.util.List;

public interface FilmService {
    void save(FilmDto filmDto);
    void deleteById(Long id);
    void deleteAll();
    FilmDto findById(Long id);
    List<FilmDto> findAll();

}
