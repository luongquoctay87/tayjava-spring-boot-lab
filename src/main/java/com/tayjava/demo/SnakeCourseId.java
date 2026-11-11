package com.tayjava.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Shows {@code @JsonProperty} renaming: JSON uses {@code course_id}, Java uses {@code courseId}.
 */
public record SnakeCourseId(@JsonProperty("course_id") Long courseId) {}
