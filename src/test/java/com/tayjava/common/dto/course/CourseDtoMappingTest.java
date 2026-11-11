package com.tayjava.common.dto.course;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.entity.Course;
import org.junit.jupiter.api.Test;

class CourseDtoMappingTest {

  @Test
  void fromCopiesApiFieldsAndOmitsCreatedAt() {
    Course course = new Course("Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true);
    course.setId(7L);

    CourseResp resp = CourseResp.from(course);

    assertThat(resp.id()).isEqualTo(7L);
    assertThat(resp.title()).isEqualTo("Spring Boot");
    assertThat(resp.slug()).isEqualTo("spring-boot");
    assertThat(resp.level()).isEqualTo(ECourseLevel.BEGINNER);
    assertThat(resp.published()).isTrue();
    // CourseResp has no createdAt — API shape stays separate from the domain model.
    assertThat(CourseResp.class.getRecordComponents())
        .extracting(rc -> rc.getName())
        .doesNotContain("createdAt");
  }
}
