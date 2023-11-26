package com.epam.training.ticketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rooms")
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "rowNumber", nullable = false)
    private int rows;

    @Column(name = "colNumber", nullable = false)
    private int cols;

    public Room(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }
}
