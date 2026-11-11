package com.tayjava.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.common.dto.course.CourseSummary;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.entity.Course;
import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class CourseRepositoryQueryTest extends AbstractPostgresIntegrationTest {

  @Autowired private CourseRepository courses;

  @Test
  @Transactional
  void searchAndProjectionWork() {
    String slug = "query-methods-" + System.nanoTime();
    courses.save(new Course("Spring Boot Query", slug, ECourseLevel.BEGINNER, true));

    assertThat(courses.search("Boot", ECourseLevel.BEGINNER))
        .extracting(Course::getSlug)
        .contains(slug);
    assertThat(courses.findByPublishedTrueOrderByTitleAsc())
        .extracting(CourseSummary::slug)
        .contains(slug);
    assertThat(courses.findSummariesByLevel(ECourseLevel.BEGINNER))
        .extracting(CourseSummary::slug)
        .contains(slug);
  }
}
