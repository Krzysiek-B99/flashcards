package com.example.flashcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FlashcardSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //true = set private (everybody can read it)     |     false = set private  (nobody can read it)
    private boolean privacy;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Flashcard> flashcards = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private User user;

    public FlashcardSet removeFlashcard(Flashcard flashcard){
        flashcards.remove(flashcard);
        return this;
    }

}
