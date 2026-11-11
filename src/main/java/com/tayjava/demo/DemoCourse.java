package com.tayjava.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Minimal entity for the Spring Data JPA intro demo (lesson 25).
 * Not part of the Course API project line — see lesson 26 for the real {@code Course} entity.
 */
@Entity
@Table(name = "demo_course")
public class DemoCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true, length = 120)
    private String slug;

    protected DemoCourse() {
        // JPA
    }

    public DemoCourse(String title, String slug) {
        this.title = title;
        this.slug = slug;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }
}
