package com.tayjava.entity;

import com.tayjava.common.enums.ECourseLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** JPA Course entity. Controllers keep speaking DTOs via {@code CourseResp.from}. */
@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(nullable = false, length = 220, unique = true)
  private String slug;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private ECourseLevel level;

  @Column(nullable = false)
  private boolean published;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  public Course(String title, String slug, ECourseLevel level, boolean published) {
    this.title = title;
    this.slug = slug;
    this.level = level;
    this.published = published;
    this.createdAt = Instant.now();
  }

  public void update(String title, ECourseLevel level, boolean published) {
    this.title = title;
    this.level = level;
    this.published = published;
  }

  public boolean isPublished() {
    return published;
  }
}
