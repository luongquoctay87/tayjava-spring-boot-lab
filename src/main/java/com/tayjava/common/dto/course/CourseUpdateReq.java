package com.tayjava.common.dto.course;

import com.tayjava.common.enums.ECourseLevel;

public record CourseUpdateReq(String title, ECourseLevel level, boolean published) {}
