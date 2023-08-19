package com.n1eko.websiteranking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    private String ip;

    @ManyToOne
    @JoinColumn(name = "id")
    private Website website;

    private LocalDateTime time;
}
