package com.example.demo.service.impl;


import com.example.demo.data.Film;
import com.example.demo.dto.FilmDto;
import com.example.demo.repository.FilmRepository;
import com.example.demo.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    @Override
    public void save(FilmDto filmDto) {
        filmRepository.save(filmDto.toEntity());
    }

    @Override
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        filmRepository.deleteAll();
    }

    @Override
    public FilmDto findById(Long id) {
        return filmRepository.findFirstById(id).toDto();
    }

    @Override
    public List<FilmDto> findAll() {
        return filmRepository.findAll().stream().map(Film::toDto).collect(Collectors.toList());
    }
}
