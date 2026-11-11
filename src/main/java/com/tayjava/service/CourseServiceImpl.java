package com.tayjava.service;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/**
 * Early in-memory CourseService (constructor-DI target for EnrollmentService).
 * Lesson 16 replaces storage with an entity Course model and adds REST.
 */
@Service
public class CourseServiceImpl implements CourseService {

  private final Map<Long, CourseResp> courses = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong();

  @Override
  public List<CourseResp> list() {
    return courses.values().stream().sorted(Comparator.comparing(CourseResp::id)).toList();
  }

  @Override
  public CourseResp get(Long id) {
    CourseResp course = courses.get(id);
    if (course == null) {
      throw new NoSuchElementException("Course not found: " + id);
    }
    return course;
  }

  @Override
  public CourseResp create(CourseReq req) {
    long id = sequence.incrementAndGet();
    CourseResp course = new CourseResp(id, req.title(), req.slug(), req.level(), req.published());
    courses.put(id, course);
    return course;
  }
}
