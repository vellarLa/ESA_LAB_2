package com.example.demo.service;

import com.example.demo.dto.SeatDto;
import com.example.demo.dto.TicketDto;
import com.example.demo.dto.TimetableDto;

import java.util.List;

public interface  TicketService {
    void save(TicketDto ticketDto);
    void deleteById(Long id);
    void deleteAll();
    TicketDto findById(Long id);
    List<TicketDto> findAll();
    List<SeatDto> getFreeSeats(TimetableDto timetableDto);
}
