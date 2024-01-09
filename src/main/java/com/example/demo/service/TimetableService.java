package com.example.demo.service;

import com.example.demo.dto.FilmDto;
import com.example.demo.dto.TimetableDto;

import java.util.List;

public interface  TimetableService {
    void save(TimetableDto timetableDto);
    void deleteById(Long id);
    void deleteAll();
    TimetableDto findById(Long id);
    List<TimetableDto> findAll();
    List<TimetableDto> findAllByFilm(FilmDto filmDto);
}
