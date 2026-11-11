package com.tayjava.service;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.course.CourseUpdateReq;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.entity.Course;
import com.tayjava.repository.CourseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CourseService")
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courses;

  @Override
  @Transactional(readOnly = true)
  public List<CourseResp> list() {
    return courses.findAll().stream().map(CourseResp::from).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public CourseResp get(Long id) {
    return CourseResp.from(load(id));
  }

  @Override
  @Transactional
  public CourseResp create(CourseReq req) {
    if (courses.existsBySlug(req.slug())) {
      throw new ErrorException(ErrorCode.SLUG_TAKEN, "Slug already exists: " + req.slug());
    }
    Course saved =
        courses.save(new Course(req.title(), req.slug(), req.level(), req.published()));
    log.info("Created course id={} slug={}", saved.getId(), saved.getSlug());
    return CourseResp.from(saved);
  }

  @Override
  @Transactional
  public CourseResp update(Long id, CourseUpdateReq req) {
    Course course = load(id);
    course.update(req.title(), req.level(), req.published());
    return CourseResp.from(course);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    courses.delete(load(id));
  }

  @Override
  @Transactional(readOnly = true)
  public Course load(Long id) {
    return courses
        .findById(id)
        .orElseThrow(
            () -> new ErrorException(ErrorCode.COURSE_NOT_FOUND, "Course not found: " + id));
  }
}
