package com.tayjava.service;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.course.CourseUpdateReq;
import com.tayjava.entity.Course;
import java.util.List;

public interface CourseService {

  List<CourseResp> list();

  CourseResp get(Long id);

  CourseResp create(CourseReq req);

  CourseResp update(Long id, CourseUpdateReq req);

  void delete(Long id);

  Course load(Long id);
}
