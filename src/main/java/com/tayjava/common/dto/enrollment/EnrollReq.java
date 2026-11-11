package com.tayjava.common.dto.enrollment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnrollReq(@NotBlank @Email @Size(max = 254) String studentEmail) {}
