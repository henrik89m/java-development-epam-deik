package com.epam.training.ticketservice.dto;

import lombok.Getter;

@Getter
public class ScreeningDto {

    private final MovieDto movie;
    private final String roomName;
    private final String startTime;

    public ScreeningDto(MovieDto movie, String roomName, String startTime) {
        this.movie = movie;
        this.roomName = roomName;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return movie.getTitle() + " (" + movie.getGenre() + ", " + movie.getLength() +
                " minutes), screened in Room: " + roomName + ", at " + startTime;

    }
}
