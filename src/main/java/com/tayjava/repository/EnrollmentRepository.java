package com.tayjava.repository;

import com.tayjava.entity.Enrollment;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  boolean existsByCourseIdAndStudentEmail(Long courseId, String studentEmail);

  List<Enrollment> findByStudentEmailOrderByEnrolledAtDesc(String studentEmail);

  @Query(
      """
      select e from Enrollment e
      join fetch e.course
      where e.studentEmail = :studentEmail
      order by e.enrolledAt desc
      """)
  List<Enrollment> findWithCourseByStudentEmail(@Param("studentEmail") String studentEmail);

  @EntityGraph(attributePaths = "course")
  List<Enrollment> findGraphByStudentEmail(String studentEmail);
}
