package com.tayjava.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SpringDataJpaIntroDemoTest {

  @SpringBootConfiguration
  @EnableAutoConfiguration
  @Import(DemoJpaConfig.class)
  static class DemoJpaSlice {}

  @Autowired
  private DemoCourseRepository repository;

  @Test
  void saveAssignsIdAndDerivedQueriesWork() {
    assertThat(repository.existsBySlug("spring-data-jpa")).isFalse();

    DemoCourse saved = repository.save(new DemoCourse("Spring Data JPA Intro", "spring-data-jpa"));

    assertThat(saved.getId()).isNotNull();
    assertThat(repository.findById(saved.getId())).isPresent();
    assertThat(repository.existsBySlug("spring-data-jpa")).isTrue();
    assertThat(repository.findBySlug("spring-data-jpa"))
        .isPresent()
        .get()
        .extracting(DemoCourse::getTitle)
        .isEqualTo("Spring Data JPA Intro");
    assertThat(repository.count()).isEqualTo(1);
  }

  @Test
  void repositoryBeanIsSpringDataProxy() {
    assertThat(repository.getClass().getName()).contains("Proxy");
  }
}
