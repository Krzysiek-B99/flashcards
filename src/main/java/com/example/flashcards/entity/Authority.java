package com.example.flashcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Authority implements GrantedAuthority {

    private static final long serialVersionID = -6520888182797362903L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;

    public Authority() {
    }

    public Authority (String authority){
        this.authority = authority;
    }

}
