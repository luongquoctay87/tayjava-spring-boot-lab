package com.tayjava.common.dto.course;

import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.entity.Course;

public record CourseResp(Long id, String title, String slug, ECourseLevel level, boolean published) {

  public static CourseResp from(Course course) {
    return new CourseResp(
        course.getId(),
        course.getTitle(),
        course.getSlug(),
        course.getLevel(),
        course.isPublished());
  }
}
