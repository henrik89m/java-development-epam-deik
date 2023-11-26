package com.epam.training.ticketservice.service;


import com.epam.training.ticketservice.dto.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Optional<MovieDto> createMovie(String title, String genre, int length);

    Optional<MovieDto> updateMovie(String title, String genre, int length);

    Optional<MovieDto> deleteMovie(String title);

    List<MovieDto> listMovies();


}
