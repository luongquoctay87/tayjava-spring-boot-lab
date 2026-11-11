package com.tayjava.demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Servlet filter that times the whole request (runs outside DispatcherServlet).
 * Temporary demo — not kept on lab/capstone project branches.
 */
@Component
@Slf4j(topic = "RequestTimingFilter")
public class RequestTimingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    long start = System.nanoTime();
    log.info("filter before: {} {}", request.getMethod(), request.getRequestURI());
    try {
      chain.doFilter(request, response);
    } finally {
      long tookMs = (System.nanoTime() - start) / 1_000_000;
      log.info("filter after: status={} took={}ms", response.getStatus(), tookMs);
    }
  }
}
