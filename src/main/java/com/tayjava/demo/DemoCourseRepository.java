package com.tayjava.demo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository demo: declare the interface; Spring generates the bean.
 * Preview of the pattern used later by {@code CourseRepository} (lesson 26).
 */
@Repository
public interface DemoCourseRepository extends JpaRepository<DemoCourse, Long> {

  boolean existsBySlug(String slug);

  Optional<DemoCourse> findBySlug(String slug);
}
