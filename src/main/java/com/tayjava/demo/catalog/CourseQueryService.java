package com.tayjava.demo.catalog;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Business service using only Framework annotations.
 * Boot is the runtime host; this class would work the same on a hand-built context.
 */
@Service
public class CourseQueryService {

    private final CourseCatalog catalog;

    public CourseQueryService(CourseCatalog catalog) {
        this.catalog = catalog;
    }

    public List<CourseSummary> listPublished() {
        return catalog.findPublished();
    }
}
