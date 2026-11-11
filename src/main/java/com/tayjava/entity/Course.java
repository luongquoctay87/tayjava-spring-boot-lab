package com.tayjava.entity;

import com.tayjava.common.enums.ECourseLevel;
import java.time.Instant;
import lombok.Getter;

/** In-memory Course model (JPA annotations arrive in later lessons). */
@Getter
public class Course {

  private final Long id;
  private String title;
  private final String slug;
  private ECourseLevel level;
  private boolean published;
  private final Instant createdAt;

  public Course(Long id, String title, String slug, ECourseLevel level, boolean published) {
    this.id = id;
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
