package com.tayjava.common.dto.course;

import com.tayjava.common.enums.ECourseLevel;

public record CourseReq(String title, String slug, ECourseLevel level, boolean published) {}
