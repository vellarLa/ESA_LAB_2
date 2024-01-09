package com.example.demo.controller;

import com.example.demo.dto.FilmDto;
import com.example.demo.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/film")
public class FilmController {
    private final FilmService filmService;

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("film", new FilmDto());
        return "all-films";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("film", filmService.findById(id));
        return "update-film";
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable("id") Long id, @ModelAttribute FilmDto filmDto, Model model) {
        filmDto.setId(id);
        filmService.save(filmDto);
        return "redirect:/film";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        filmService.deleteById(id);
        return "redirect:/film";
    }

    @PostMapping("")
    public String create(@ModelAttribute FilmDto filmDto, Model model) {
        filmService.save(filmDto);
        return "redirect:/film";
    }


}
