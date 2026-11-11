package com.tayjava.service;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.course.CourseUpdateReq;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.entity.Course;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "CourseService")
public class CourseServiceImpl implements CourseService {

  private final Map<Long, Course> store = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong();

  @Override
  public List<CourseResp> list() {
    return store.values().stream()
        .sorted(Comparator.comparing(Course::getId))
        .map(CourseResp::from)
        .toList();
  }

  @Override
  public CourseResp get(Long id) {
    return CourseResp.from(load(id));
  }

  @Override
  public synchronized CourseResp create(CourseReq req) {
    boolean slugTaken =
        store.values().stream().anyMatch(course -> course.getSlug().equals(req.slug()));
    if (slugTaken) {
      throw new ErrorException(ErrorCode.SLUG_TAKEN, "Slug already exists: " + req.slug());
    }
    Course course =
        new Course(
            sequence.incrementAndGet(), req.title(), req.slug(), req.level(), req.published());
    store.put(course.getId(), course);
    log.info("Created course id={} slug={}", course.getId(), course.getSlug());
    return CourseResp.from(course);
  }

  @Override
  public CourseResp update(Long id, CourseUpdateReq req) {
    Course course = load(id);
    course.update(req.title(), req.level(), req.published());
    return CourseResp.from(course);
  }

  @Override
  public void delete(Long id) {
    store.remove(load(id).getId());
  }

  @Override
  public Course load(Long id) {
    Course course = store.get(id);
    if (course == null) {
      throw new ErrorException(ErrorCode.COURSE_NOT_FOUND, "Course not found: " + id);
    }
    return course;
  }
}
