package com.tayjava.demo;

import java.util.concurrent.atomic.AtomicInteger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Minimal timing aspect for lesson 42. Counts advice runs so tests can prove
 * proxy-based AOP vs self-invocation without scraping logs.
 */
@Aspect
@Component
public class TimingAspect {

    private static final Logger log = LoggerFactory.getLogger(TimingAspect.class);

    private final AtomicInteger adviceCount = new AtomicInteger();

    @Around("execution(* com.tayjava.demo.DemoEnrollmentService.*(..))")
    public Object logDuration(ProceedingJoinPoint pjp) throws Throwable {
        adviceCount.incrementAndGet();
        long start = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            log.info("{} took {} ms", pjp.getSignature().toShortString(), ms);
        }
    }

    public int adviceCount() {
        return adviceCount.get();
    }

    public void reset() {
        adviceCount.set(0);
    }
}
