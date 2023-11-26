package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<MovieDto> createMovie(String title, String genre, int length) {
        if (movieRepository.existsByTitle(title)){
            throw new IllegalArgumentException("Movie already exists");
        }
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setLength(length);

        Movie savedMovie = movieRepository.save(movie);
        MovieDto movieDto = new MovieDto(savedMovie.getTitle(), savedMovie.getGenre(), savedMovie.getLength());

        return Optional.of(movieDto);
    }

    public Optional<MovieDto> updateMovie(String title, String genre, int length) {
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found to be updated"));

        movie.setGenre(genre);
        movie.setLength(length);

        Movie savedMovie = movieRepository.save(movie);
        MovieDto movieDto = new MovieDto(savedMovie.getTitle(), savedMovie.getGenre(), savedMovie.getLength());

        return Optional.of(movieDto);
    }


    @Override
    public Optional<MovieDto> deleteMovie(String title) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(title);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movieRepository.delete(movie);

            MovieDto deletedMovieDto = new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength());
            return Optional.of(deletedMovieDto);
        } else {
            throw new IllegalArgumentException("Movie does not exist");
        }
    }


    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength()))
                .collect(Collectors.toList());
    }
}
