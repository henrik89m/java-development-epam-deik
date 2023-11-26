package com.epam.training.ticketservice.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class MovieDto {

    private final String title;
    private final String genre;
    private final int length;

    public MovieDto(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDto movieDto = (MovieDto) o;
        return length == movieDto.length && Objects.equals(title, movieDto.title)
                && Objects.equals(genre, movieDto.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, length);
    }

    @Override
    public String toString() {
        return title + " (" + genre + "," + length + " minutes)";
    }
}
