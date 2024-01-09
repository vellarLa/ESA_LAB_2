package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.FilmService;
import com.example.demo.service.TicketService;
import com.example.demo.service.TimetableService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TimetableService timetableService;
    private final FilmService filmService;
    private final TicketService ticketService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("")
    public String getAll(Model model) {
        List<FilmDto> filmDtoList = filmService.findAll();
        List<TimetableDto> timetableDtoList = new ArrayList<>();
        model.addAttribute("ticket", new TicketDto());
        model.addAttribute("changeTicket", new ChangeTicketDto());
        if (! filmDtoList.isEmpty()) {
            timetableDtoList = timetableService.findAllByFilm(filmDtoList.get(0));
            if (!timetableDtoList.isEmpty()) {
                List<SeatDto> seatDtos = ticketService.getFreeSeats(timetableDtoList.get(0));
                if (!seatDtos.isEmpty()) {
                    model.addAttribute("ticket", new TicketDto(null, seatDtos.get(0),
                            0.0, false, timetableDtoList.get(0)));
                    model.addAttribute("changeTicket", new ChangeTicketDto(null, seatDtos.get(0).toString(),
                            timetableDtoList.get(0)));
                    model.addAttribute("seats", seatDtos);
                }
            }
        }
        model.addAttribute("films", filmDtoList);
        model.addAttribute("film", new FilmDto());
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("fullTimetable", timetableDtoList);

        return "all-tickets";
    }

    @PostMapping("/film")
    public String updateAllByFilmChange(@ModelAttribute FilmDto filmDto, Model model) {
        updateAllByFilm(filmDto, model);
        return "all-tickets";
    }


    @PostMapping("/timetable")
    public String updateAllBySeanceChange(@ModelAttribute ChangeTicketDto changeTicketDto, Model model) {
        updateAllBySeance(changeTicketDto, model);
        return "all-tickets";
    }

    @PostMapping("/update/timetable")
    public String updateAllBySeanceChangeForUpdate(@ModelAttribute ChangeTicketDto changeTicketDto, Model model) {
        updateAllBySeance(changeTicketDto, model);
        return "update-ticket";
    }

    @PostMapping("")
    public String saveTicket(@ModelAttribute TicketDto ticketDto, Model model) {
        TimetableDto timetableDto = timetableService.findById(ticketDto.getTimetable().getId());
        ticketDto.setTimetable(timetableDto);
        ticketService.save(ticketDto);
        return "redirect:/ticket";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        ticketService.deleteById(id);
        return "redirect:/ticket";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        TicketDto ticketDto = ticketService.findById(id);
        model.addAttribute("ticket", ticketDto);
        List<FilmDto> filmDtoList = filmService.findAll();
        model.addAttribute("changeTicket", new ChangeTicketDto(ticketDto.getId(), ticketDto.getSeatDto().toString(), ticketDto.getTimetable()));
        model.addAttribute("film", ticketDto.getTimetable().getFilm());
        List<TimetableDto> timetableDtoList = timetableService.findAllByFilm(filmService.findById(ticketDto.getTimetable().getFilm().getId()));
        model.addAttribute("fullTimetable", timetableDtoList);
        List<SeatDto> seatDtos = new ArrayList<>();
        seatDtos.add(ticketDto.getSeatDto());
        seatDtos.addAll(ticketService.getFreeSeats(ticketDto.getTimetable()));
        model.addAttribute("seats", seatDtos);
        return "update-ticket";
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable("id") Long id, @ModelAttribute TicketDto ticketDto, Model model) {
        ticketDto.setId(id);
        TimetableDto timetableDto = timetableService.findById(ticketDto.getTimetable().getId());
        ticketDto.setTimetable(timetableDto);
        ticketService.save(ticketDto);
        return "redirect:/ticket";
    }


    private void updateAllBySeance(ChangeTicketDto changeTicketDto, Model model) {
        String[] seatsParams = changeTicketDto.getSeatDto().split(";");
        SeatDto seatDto = new SeatDto();
        if (seatsParams.length == 2) {
            seatDto = new SeatDto(Integer.parseInt(seatsParams[0]), Integer.parseInt(seatsParams[1]));
        }
        List<FilmDto> filmDtoList = filmService.findAll();
        TimetableDto timetableDto1 = timetableService.findById(changeTicketDto.getTimetable().getId());
        List<TimetableDto> timetableDtoList = timetableService.findAllByFilm(filmService.findById(timetableDto1.getFilm().getId()));
        model.addAttribute("films", filmDtoList);
        model.addAttribute("film", timetableDto1.getFilm());
        model.addAttribute("ticket", new TicketDto(changeTicketDto.getId(), seatDto, 0.0, false, timetableDto1));
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("fullTimetable", timetableDtoList);
        model.addAttribute("changeTicket", new ChangeTicketDto(changeTicketDto.getId(), changeTicketDto.getSeatDto(), timetableDto1));
        model.addAttribute("seats",  ticketService.getFreeSeats(timetableDto1));
    }


    private void updateAllByFilm(FilmDto filmDto, Model model) {
        List<FilmDto> filmDtoList = filmService.findAll();
        model.addAttribute("ticket", new TicketDto());
        model.addAttribute("changeTicket", new ChangeTicketDto());
        List<TimetableDto> timetableDtoList = timetableService.findAllByFilm(filmService.findById(filmDto.getId()));
        if (!timetableDtoList.isEmpty()) {
            List<SeatDto> seatDtos = ticketService.getFreeSeats(timetableDtoList.get(0));
            if (!seatDtos.isEmpty()) {
                model.addAttribute("ticket", new TicketDto(null, seatDtos.get(0),
                        0.0, false, timetableDtoList.get(0)));
                model.addAttribute("changeTicket", new ChangeTicketDto(null, seatDtos.get(0).toString(),
                        timetableDtoList.get(0)));
                model.addAttribute("seats", seatDtos);
            }
        }
        model.addAttribute("films", filmDtoList);
        model.addAttribute("film", filmDto);
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("fullTimetable", timetableDtoList);
    }
}
