package com.tayjava.common.dto.course;

import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.entity.Course;

/**
 * API view of a Course. Built by hand from the domain entity — do not return {@link Course} from
 * controllers.
 */
public record CourseResp(Long id, String title, String slug, ECourseLevel level, boolean published) {

  /** Manual entity → DTO mapping (lesson 23). */
  public static CourseResp from(Course course) {
    return new CourseResp(
        course.getId(),
        course.getTitle(),
        course.getSlug(),
        course.getLevel(),
        course.isPublished());
  }
}
