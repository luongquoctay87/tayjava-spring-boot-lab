package com.tayjava.service;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import java.util.List;

public interface CourseService {

  List<CourseResp> list();

  CourseResp get(Long id);

  CourseResp create(CourseReq req);
}
