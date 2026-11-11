package com.tayjava.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * HandlerInterceptor hooks (inside DispatcherServlet, knows the handler).
 */
@Slf4j(topic = "HandlerLoggingInterceptor")
public class HandlerLoggingInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    log.info("preHandle: {}", handler);
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {
    log.info("postHandle: committed={}", response.isCommitted());
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    log.info("afterCompletion: status={} ex={}", response.getStatus(), ex);
  }
}
