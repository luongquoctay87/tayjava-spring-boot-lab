package com.tayjava.entity;

import com.tayjava.common.enums.EEnrollmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "enrollment",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_enrollment_course_student",
            columnNames = {"course_id", "student_email"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrollment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(name = "student_email", nullable = false, length = 254)
  private String studentEmail;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private EEnrollmentStatus status;

  @Column(name = "enrolled_at", nullable = false, updatable = false)
  private Instant enrolledAt;

  public Enrollment(Course course, String studentEmail) {
    this.course = course;
    this.studentEmail = studentEmail;
    this.status = EEnrollmentStatus.ACTIVE;
    this.enrolledAt = Instant.now();
  }

  public void cancel() {
    this.status = EEnrollmentStatus.CANCELLED;
  }
}
