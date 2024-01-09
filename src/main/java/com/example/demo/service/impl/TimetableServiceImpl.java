package com.example.demo.service.impl;

import com.example.demo.data.Timetable;
import com.example.demo.dto.FilmDto;
import com.example.demo.dto.TimetableDto;
import com.example.demo.repository.TimetableRepository;
import com.example.demo.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    private final TimetableRepository timetableRepository;
    @Override
    public void save(TimetableDto timetableDto) {
        timetableRepository.save(timetableDto.toEntity());
    }
    @Override
    public void deleteById(Long id) {
        timetableRepository.deleteById(id);
    }
    @Override
    public void deleteAll() {
        timetableRepository.deleteAll();
    }
    @Override
    public TimetableDto findById(Long id) {
        return timetableRepository.findFirstById(id).toDto();
    }
    @Override
    public List<TimetableDto> findAll() {
        return timetableRepository.findAll().stream().map(Timetable::toDto).collect(Collectors.toList());
    }
    @Override
    public List<TimetableDto> findAllByFilm(FilmDto filmDto) {
        return timetableRepository.findAllByFilm_Id(filmDto.getId())
                .stream().map(Timetable::toDto)
                .collect(Collectors.toList());
    }
}
