package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository,
                                RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Optional<ScreeningDto> createScreening(String movieTitle, String roomName, LocalDateTime start) {
        if (screeningRepository.existsByMovieTitleAndRoomNameAndStartTime(movieTitle, roomName, start)) {
            throw new IllegalArgumentException("Screening already exists");
        }

        Movie movie = movieRepository.findByTitle(movieTitle)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Room room = roomRepository.findByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setRoom(room);
        screening.setStartTime(start);

        Screening savedScreening = screeningRepository.save(screening);
        ScreeningDto screeningDto = new ScreeningDto(
                new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength()),
                room.getName(),
                formatter.format(start));

        return Optional.of(screeningDto);
    }

    @Override
    public Optional<ScreeningDto> deleteScreening(String movieTitle, String roomName, LocalDateTime start) {
        Optional<Screening> screeningOptional = screeningRepository
                .findByMovieTitleAndRoomNameAndStartTime(movieTitle, roomName, start);

        if (screeningOptional.isEmpty()) {
            throw new IllegalArgumentException("No screening found");
        }

        Screening screening = screeningOptional.get();
        screeningRepository.delete(screening);

        ScreeningDto deletedScreeningDto = new ScreeningDto(
                new MovieDto(screening.getMovie().getTitle(), screening.getMovie().getGenre(),
                        screening.getMovie().getLength()),
                screening.getRoom().getName(),
                formatter.format(screening.getStartTime())
        );
        return Optional.of(deletedScreeningDto);
    }

    @Override
    public List<ScreeningDto> listScreening() {
        return screeningRepository.findAll().stream()
                .map(screening -> new ScreeningDto(
                        new MovieDto(
                                screening.getMovie().getTitle(),
                                screening.getMovie().getGenre(),
                                screening.getMovie().getLength()
                        ),
                        screening.getRoom().getName(),
                        formatter.format(screening.getStartTime())))
                .collect(Collectors.toList());
    }

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
