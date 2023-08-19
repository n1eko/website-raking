package com.n1eko.websiteranking.model;

import jakarta.persistence.*;

@Entity
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private int votes;

}
