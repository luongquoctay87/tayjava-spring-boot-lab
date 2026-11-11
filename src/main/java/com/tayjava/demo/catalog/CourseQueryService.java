package com.tayjava.demo.catalog;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Business service using only Framework annotations.
 * Boot is the runtime host; this class would work the same on a hand-built context.
 */
@Service
@RequiredArgsConstructor
public class CourseQueryService {

  private final CourseCatalog catalog;

  public List<CourseSummary> listPublished() {
    return catalog.findPublished();
  }
}
