package com.tayjava.common.dto.course;

import com.tayjava.common.enums.ECourseLevel;

public record CourseResp(Long id, String title, String slug, ECourseLevel level, boolean published) {}
