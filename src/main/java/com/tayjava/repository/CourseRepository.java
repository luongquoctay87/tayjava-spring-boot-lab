package com.tayjava.repository;

import com.tayjava.entity.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  boolean existsBySlug(String slug);

  Optional<Course> findBySlug(String slug);
}
