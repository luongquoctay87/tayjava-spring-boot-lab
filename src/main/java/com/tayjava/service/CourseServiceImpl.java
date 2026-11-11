package com.tayjava.service;

import com.tayjava.common.dto.PageResp;
import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.course.CourseUpdateReq;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.entity.Course;
import com.tayjava.repository.CourseRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service transaction boundary (lesson 30): read methods use {@code readOnly=true}; writes roll
 * back on unchecked {@link com.tayjava.common.exception.ErrorException}.
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "CourseService")
public class CourseServiceImpl implements CourseService {

  private static final Set<String> SORTABLE = Set.of("createdAt", "title");

  private final CourseRepository courses;

  @Override
  @Cacheable(cacheNames = "coursesList")
  @Transactional(readOnly = true)
  public PageResp<CourseResp> list(Pageable pageable) {
    return PageResp.from(courses.findAll(sanitize(pageable)).map(CourseResp::from));
  }

  @Override
  @Cacheable(cacheNames = "courses", key = "#id")
  @Transactional(readOnly = true)
  public CourseResp get(Long id) {
    return CourseResp.from(load(id));
  }

  @Override
  @CacheEvict(cacheNames = {"courses", "coursesList"}, allEntries = true)
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
  @CacheEvict(cacheNames = {"courses", "coursesList"}, allEntries = true)
  @Transactional
  public CourseResp update(Long id, CourseUpdateReq req) {
    Course course = load(id);
    course.update(req.title(), req.level(), req.published());
    return CourseResp.from(course);
  }

  @Override
  @CacheEvict(cacheNames = {"courses", "coursesList"}, allEntries = true)
  @Transactional
  public void delete(Long id) {
    Course course = load(id);
    try {
      courses.delete(course);
      courses.flush();
    } catch (DataIntegrityViolationException ex) {
      throw new ErrorException(
          ErrorCode.COURSE_HAS_ENROLLMENTS, "Course has enrollments: " + id);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Course load(Long id) {
    return courses
        .findById(id)
        .orElseThrow(
            () -> new ErrorException(ErrorCode.COURSE_NOT_FOUND, "Course not found: " + id));
  }

  private Pageable sanitize(Pageable pageable) {
    if (!pageable.isPaged()) {
      return pageable;
    }
    Sort sort =
        Sort.by(
            pageable.getSort().stream()
                .filter(order -> SORTABLE.contains(order.getProperty()))
                .toList());
    if (sort.isUnsorted()) {
      sort = Sort.by(Sort.Direction.DESC, "createdAt");
    }
    return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
  }
}
