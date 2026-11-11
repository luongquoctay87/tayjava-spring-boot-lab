package com.tayjava.common.dto.course;

import com.tayjava.common.enums.ECourseLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseUpdateReq(
    @NotBlank @Size(max = 200) String title, @NotNull ECourseLevel level, boolean published) {}
