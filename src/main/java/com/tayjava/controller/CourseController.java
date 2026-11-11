package com.tayjava.controller;

import com.tayjava.common.dto.ApiResp;
import com.tayjava.common.dto.Resp;
import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.course.CourseUpdateReq;
import com.tayjava.service.CourseService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<Resp<List<CourseResp>>> list() {
    return ApiResp.ok(courseService.list());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Resp<CourseResp>> get(@PathVariable Long id) {
    return ApiResp.ok(courseService.get(id));
  }

  @PostMapping
  public ResponseEntity<Resp<CourseResp>> create(@Valid @RequestBody CourseReq req) {
    CourseResp created = courseService.create(req);
    return ApiResp.created(URI.create("/api/courses/" + created.id()), created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Resp<CourseResp>> update(
      @PathVariable Long id, @Valid @RequestBody CourseUpdateReq req) {
    return ApiResp.ok(courseService.update(id, req));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Resp<Void>> delete(@PathVariable Long id) {
    courseService.delete(id);
    return ApiResp.noContent();
  }
}
