package com.tayjava.repository;

import com.tayjava.common.dto.course.CourseSummary;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.entity.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  boolean existsBySlug(String slug);

  Optional<Course> findBySlug(String slug);

  List<Course> findByLevelAndPublishedTrueOrderByCreatedAtDesc(ECourseLevel level);

  List<CourseSummary> findByPublishedTrueOrderByTitleAsc();

  @Query(
      """
      select c from Course c
      where lower(c.title) like lower(concat('%', :keyword, '%'))
      and c.level = :level
      order by c.createdAt desc
      """)
  List<Course> search(@Param("keyword") String keyword, @Param("level") ECourseLevel level);

  @Query(
      """
      select new com.tayjava.common.dto.course.CourseSummary(c.id, c.title, c.slug)
      from Course c where c.level = :level
      """)
  List<CourseSummary> findSummariesByLevel(@Param("level") ECourseLevel level);

  @Query(value = "select * from course c where c.slug = :slug", nativeQuery = true)
  Optional<Course> findRawBySlug(@Param("slug") String slug);

  @Modifying(clearAutomatically = true)
  @Query("update Course c set c.published = false where c.level = :level")
  int unpublishByLevel(@Param("level") ECourseLevel level);
}
