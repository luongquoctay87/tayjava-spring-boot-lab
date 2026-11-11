package com.tayjava.common.dto.course;

import com.tayjava.common.enums.ECourseLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CourseReq(
    @NotBlank @Size(max = 200) String title,
    @NotBlank @Size(max = 220) @Pattern(regexp = "[a-z0-9]+(-[a-z0-9]+)*") String slug,
    @NotNull ECourseLevel level,
    boolean published) {}
