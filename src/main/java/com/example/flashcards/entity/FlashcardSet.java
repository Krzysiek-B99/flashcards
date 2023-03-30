package com.example.flashcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(max = 30,message = "max 30 signs")
    @NotNull
    private String name;

    //true = set private (everybody can read it)     |     false = set private  (nobody can read it)
    private boolean privacy;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Flashcard> flashcards = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private User user;

    public void removeFlashcard(Flashcard flashcard){
        flashcards.remove(flashcard);
    }

}
