package com.tayjava.demo.catalog;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Repository;

/**
 * In-memory catalog. Uses a Framework stereotype; Boot only hosts the context.
 */
@Repository
public class InMemoryCourseCatalog implements CourseCatalog {

    private final List<CourseSummary> courses = new CopyOnWriteArrayList<>(List.of(
            new CourseSummary(1L, "Java Core", "BEGINNER"),
            new CourseSummary(2L, "Spring Boot from zero", "INTERMEDIATE")));

    @Override
    public List<CourseSummary> findPublished() {
        return List.copyOf(courses);
    }
}
