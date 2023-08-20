package com.n1eko.websiteranking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @Column(nullable = false)
    private String ip;

    @ManyToOne
    private Website website;

    @Column(nullable = false)
    private LocalDateTime time;
}
