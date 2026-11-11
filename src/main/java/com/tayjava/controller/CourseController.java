package com.tayjava.controller;

import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

  private final CourseService courseService;

  @GetMapping
  public List<CourseResp> list() {
    return courseService.list();
  }

  @GetMapping("/{id}")
  public CourseResp get(@PathVariable Long id) {
    return courseService.get(id);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    courseService.delete(id);
  }
}
