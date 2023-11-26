package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceImplTest {

    @InjectMocks
    private ScreeningServiceImpl screeningService;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    private final String MOVIE_TITLE = "Inception";
    private final String ROOM_NAME = "Room1";
    private final LocalDateTime START_TIME = LocalDateTime.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void testCreateScreeningShouldReturnScreeningDtoWhenScreeningDoesNotExist() {
        Movie movie = new Movie(MOVIE_TITLE, "Action", 150);
        Room room = new Room(ROOM_NAME, 10, 15);

        when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(ROOM_NAME)).thenReturn(Optional.of(room));
        when(screeningRepository.existsByMovieTitleAndRoomNameAndStartTime(MOVIE_TITLE, ROOM_NAME,
                START_TIME)).thenReturn(false);

        Screening savedScreening = new Screening();
        savedScreening.setMovie(movie);
        savedScreening.setRoom(room);
        savedScreening.setStartTime(START_TIME);

        when(screeningRepository.save(any())).thenReturn(savedScreening);

        Optional<ScreeningDto> result = screeningService.createScreening(MOVIE_TITLE, ROOM_NAME, START_TIME);

        assertTrue(result.isPresent());
        ScreeningDto createdScreeningDto = result.get();
        assertEquals(MOVIE_TITLE, createdScreeningDto.getMovie().getTitle());
        assertEquals(ROOM_NAME, createdScreeningDto.getRoomName());
        assertEquals(formatter.format(START_TIME), createdScreeningDto.getStartTime());

        Mockito.verify(screeningRepository, times(1)).save(any());
    }

    @Test
    void testCreateScreeningShouldThrowExceptionWhenScreeningExists() {
        when(screeningRepository.existsByMovieTitleAndRoomNameAndStartTime(MOVIE_TITLE, ROOM_NAME,
                START_TIME)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> screeningService.createScreening(MOVIE_TITLE,
                ROOM_NAME, START_TIME));

        Mockito.verify(screeningRepository, never()).save(any());
    }

    @Test
    void testCreateScreeningShouldThrowExceptionWhenMovieNotFound() {
        when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> screeningService.createScreening(MOVIE_TITLE,
                ROOM_NAME, START_TIME));

        Mockito.verify(screeningRepository, never()).save(any());
    }

    @Test
    void testCreateScreeningShouldThrowExceptionWhenRoomNotFound() {
        when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(Optional.of(new Movie()));
        when(roomRepository.findByName(ROOM_NAME)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> screeningService.createScreening(MOVIE_TITLE, ROOM_NAME,
                START_TIME));

        Mockito.verify(screeningRepository, never()).save(any());
    }

    @Test
    void testDeleteScreening() {
        Screening existingScreening = new Screening();
        existingScreening.setMovie(new Movie(MOVIE_TITLE, "Action", 150));
        existingScreening.setRoom(new Room(ROOM_NAME, 10, 15));
        existingScreening.setStartTime(START_TIME);

        when(screeningRepository.findByMovieTitleAndRoomNameAndStartTime(MOVIE_TITLE, ROOM_NAME, START_TIME))
                .thenReturn(Optional.of(existingScreening));

        Optional<ScreeningDto> result = screeningService.deleteScreening(MOVIE_TITLE, ROOM_NAME, START_TIME);

        assertTrue(result.isPresent());
        ScreeningDto deletedScreeningDto = result.get();
        assertEquals(MOVIE_TITLE, deletedScreeningDto.getMovie().getTitle());
        assertEquals(ROOM_NAME, deletedScreeningDto.getRoomName());
        assertEquals(formatter.format(START_TIME), deletedScreeningDto.getStartTime());

        Mockito.verify(screeningRepository, times(1)).delete(existingScreening);
    }

    @Test
    void testDeleteScreeningShouldThrowExceptionWhenScreeningNotFound() {
        when(screeningRepository.findByMovieTitleAndRoomNameAndStartTime(MOVIE_TITLE, ROOM_NAME, START_TIME))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> screeningService.deleteScreening(MOVIE_TITLE, ROOM_NAME,
                START_TIME));

        Mockito.verify(screeningRepository, never()).delete(any());
    }

}
