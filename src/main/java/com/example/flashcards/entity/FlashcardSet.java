package com.example.flashcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @OneToMany
    private Set<Flashcard> flashcards = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private User user;

    public FlashcardSet(String name, boolean privacy) {
        this.name = name;
        this.privacy = privacy;
    }

}
