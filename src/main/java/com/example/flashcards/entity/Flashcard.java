package com.example.flashcards.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String front;

    private String back;

    @Max(value = 5)
    @Min(value = 1)
    //lvl1 = repeat every day  |  lvl2 = repeat after 2 days  |  lvl3 = repeat after 4 days  |  lvl4 = repeat after week  |  lvl5 = repeat after 2 weeks
    private int level;

    private LocalDateTime repeatTime;

}
