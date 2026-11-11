package com.tayjava.demo.catalog;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Framework MVC controller. Boot's web starter supplies the embedded server and auto-config.
 */
@RestController
public class CourseController {

    private final CourseQueryService courseQueryService;

    public CourseController(CourseQueryService courseQueryService) {
        this.courseQueryService = courseQueryService;
    }

    @GetMapping("/api/courses")
    public List<CourseSummary> list() {
        return courseQueryService.listPublished();
    }
}
