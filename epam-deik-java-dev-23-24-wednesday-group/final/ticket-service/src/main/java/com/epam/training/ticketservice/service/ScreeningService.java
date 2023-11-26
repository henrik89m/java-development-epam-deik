package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.ScreeningDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    Optional<ScreeningDto> createScreening(String movie, String room, LocalDateTime start);

    Optional<ScreeningDto> deleteScreening(String movie, String room, LocalDateTime start);

    List<ScreeningDto> listScreening();
}
