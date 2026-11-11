package com.tayjava.demo;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Scans demo entities and repositories under {@code com.tayjava.demo}.
 * Side-branch only — do not merge into the Course API project line.
 */
@Configuration
@EntityScan(basePackageClasses = DemoCourse.class)
@EnableJpaRepositories(basePackageClasses = DemoCourseRepository.class)
public class DemoJpaConfig {
}
