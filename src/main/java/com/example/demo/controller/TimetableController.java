package com.example.demo.controller;

import com.example.demo.dto.FilmDto;
import com.example.demo.dto.TimetableDto;
import com.example.demo.service.FilmService;
import com.example.demo.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timetable")
public class TimetableController {
    private final TimetableService timetableService;
    private final FilmService filmService;

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("timetable", new TimetableDto());
        model.addAttribute("fullTimetable",timetableService.findAll());
        return "full-timetable";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("timetable", timetableService.findById(id));
        return "update-timetable";
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable("id") Long id, @ModelAttribute TimetableDto timetableDto, Model model) {
        timetableDto.setId(id);
        FilmDto filmDto = filmService.findById(timetableDto.getFilm().getId());
        timetableDto.setFilm(filmDto);
        timetableService.save(timetableDto);
        return "redirect:/timetable";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        timetableService.deleteById(id);
        return "redirect:/timetable";
    }

    @PostMapping("")
    public String create(@ModelAttribute TimetableDto timetableDto, Model model) {
        timetableService.save(timetableDto);
        return "redirect:/timetable";
    }

}
