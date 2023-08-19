package com.n1eko.websiteranking.model;

import jakarta.persistence.*;

@Entity
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String url;

    @ManyToOne
    @JoinColumn(name = "id")
    private Category category;

    private int votes;

}
