package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand extends PriviligeCommand {

    private final MovieService movieService;

    @ShellMethod(key = "create movie", value = "Usage: <title> <genre> <length>")
    @ShellMethodAvailability("isAdmin")
    public String createMovie(String title, String genre, int length){
        try{
            var res = movieService.createMovie(title, genre, length);
            return "Movie created successfully";
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
    @ShellMethod(key = "update movie", value = "Usage: <title> <genre> <length in minutes>")
    @ShellMethodAvailability("isAdmin")
    public String updateMovie(String title, String genre, int length) {
        try {
            var res = movieService.updateMovie(title, genre, length);
            return "Movie updated successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();

        }
    }

    @ShellMethod(key = "delete movie", value = "Usage: <title>")
    @ShellMethodAvailability("isAdmin")
    public String deleteMovie(String title){
            try {
                var res = movieService.deleteMovie(title);
                return "Movie deleted successfully";
            }catch (IllegalArgumentException e) {
                return e.getMessage();
        }

    }
    @ShellMethod(key = "list movies", value = "List all movies")
    public void listMovies() {
        try {
            List<MovieDto> movies = movieService.listMovies();
            if (movies.isEmpty()){
                System.out.println("There are no movies at the moment");
            }else{

            }for (MovieDto movie : movies) {
                System.out.println("Title: " + movie.getTitle() +
                        ", Genre: " + movie.getGenre() +
                        ", Length: " + movie.getLength());
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
