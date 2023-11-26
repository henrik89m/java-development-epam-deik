package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie("Test Movie", "Test Genre", 120);
    }

    @Test
    void createMovie_Success() {
        when(movieRepository.existsByTitle(anyString())).thenReturn(false);
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        Optional<MovieDto> result = movieService.createMovie("Test Movie", "Test Genre", 120);

        assertTrue(result.isPresent());
        assertEquals("Test Movie", result.get().getTitle());
        assertEquals("Test Genre", result.get().getGenre());
        assertEquals(120, result.get().getLength());

        verify(movieRepository, times(1)).existsByTitle("Test Movie");
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void createMovie_MovieAlreadyExists() {
        when(movieRepository.existsByTitle(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> movieService.createMovie("Test Movie", "Test Genre", 120));

        verify(movieRepository, times(1)).existsByTitle("Test Movie");
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void updateMovie_Success() {
        when(movieRepository.findByTitle(anyString())).thenReturn(Optional.of(testMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        Optional<MovieDto> result = movieService.updateMovie("Test Movie", "New Genre", 150);

        assertTrue(result.isPresent());
        assertEquals("Test Movie", result.get().getTitle());
        assertEquals("New Genre", result.get().getGenre());
        assertEquals(150, result.get().getLength());

        verify(movieRepository, times(1)).findByTitle("Test Movie");
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void updateMovie_MovieNotFound() {
        when(movieRepository.findByTitle(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> movieService.updateMovie("Test Movie", "New Genre", 150));

        verify(movieRepository, times(1)).findByTitle("Test Movie");
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void deleteMovie_Success() {
        when(movieRepository.findByTitle(anyString())).thenReturn(Optional.of(testMovie));

        Optional<MovieDto> result = movieService.deleteMovie("Test Movie");

        assertTrue(result.isPresent());
        assertEquals("Test Movie", result.get().getTitle());
        assertEquals("Test Genre", result.get().getGenre());
        assertEquals(120, result.get().getLength());

        verify(movieRepository, times(1)).findByTitle("Test Movie");
        verify(movieRepository, times(1)).delete(testMovie);
    }

    @Test
    void deleteMovie_MovieNotFound() {
        when(movieRepository.findByTitle(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> movieService.deleteMovie("Test Movie"));

        verify(movieRepository, times(1)).findByTitle("Test Movie");
        verify(movieRepository, never()).delete(any(Movie.class));
    }

    @Test
    void listMovies_Success() {
        when(movieRepository.findAll()).thenReturn(Arrays.asList(testMovie));

        List<MovieDto> result = movieService.listMovies();

        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());
        assertEquals("Test Genre", result.get(0).getGenre());
        assertEquals(120, result.get(0).getLength());

        verify(movieRepository, times(1)).findAll();
    }
}
