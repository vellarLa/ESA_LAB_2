package com.example.demo.dto;

import com.example.demo.data.Film;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FilmDto implements Serializable {
    private Long id;
    private String name;
    private String genre;
    private String country;

    public Film toEntity() {
        return new Film(this.id, this.name, this.genre, this.country);
    }
}

