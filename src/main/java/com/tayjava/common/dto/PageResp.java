package com.tayjava.common.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * Page of results for list APIs: zero-based page index, page size, totals, and the current slice.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResp<T extends Serializable> {
  private int pageNumber;
  private int pageSize;
  private long totalPages;
  private long totalElements;
  private List<T> data;

  public static <T extends Serializable> PageResp<T> from(Page<T> page) {
    return PageResp.<T>builder()
        .pageNumber(page.getNumber())
        .pageSize(page.getSize())
        .totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .data(page.getContent())
        .build();
  }
}
