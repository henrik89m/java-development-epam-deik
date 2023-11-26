package com.epam.training.ticketservice.dto;

import lombok.Getter;

@Getter
public class RoomDto {

    private final String name;
    private final int rows;
    private final int cols;

    public RoomDto(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public String toString() {
        return "Room " + name + " with " + (rows * cols) + " seats, " + rows + " rows and " + cols + " columns";
    }
}
