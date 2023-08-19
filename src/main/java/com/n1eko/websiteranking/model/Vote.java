package com.n1eko.websiteranking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private String voteType;

    private String ip;

    @ManyToOne
    @JoinColumn(name = "website_id")
    private Website website;

    private LocalDateTime time;
}
