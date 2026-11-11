package com.tayjava.demo.catalog;

import java.util.List;

/** Framework-style repository contract — no Boot-specific API. */
public interface CourseCatalog {
    List<CourseSummary> findPublished();
}
