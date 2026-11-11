package com.tayjava.common.dto.enrollment;

import com.tayjava.common.enums.EEnrollmentStatus;

public record EnrollmentResp(Long id, Long courseId, String studentEmail, EEnrollmentStatus status) {}
