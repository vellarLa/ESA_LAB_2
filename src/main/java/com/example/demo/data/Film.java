package com.example.demo.data;


import com.example.demo.dto.FilmDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "films", schema = "lab2")
public class Film {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "genre")
    private String genre;

    @Column(name = "country")
    private String country;

    public FilmDto toDto() {
        return FilmDto.builder()
                .id(this.id)
                .name(this.name)
                .country(this.country)
                .genre(this.genre)
                .build();
    }
}
