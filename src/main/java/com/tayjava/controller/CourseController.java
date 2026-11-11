package com.tayjava.controller;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.course.CourseUpdateReq;
import com.tayjava.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public CourseResp create(@RequestBody CourseReq req) {
    return courseService.create(req);
  }

  @PutMapping("/{id}")
  public CourseResp update(@PathVariable Long id, @RequestBody CourseUpdateReq req) {
    return courseService.update(id, req);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    courseService.delete(id);
  }
}
