package com.epam.training.ticketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "movies")
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private int length;

    public Movie(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public String getGenre() {
        return genre;
    }
}
