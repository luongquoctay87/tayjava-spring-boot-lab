package com.tayjava.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CouponClient {

  private final RestClient couponRestClient;

  public CouponClient(RestClient.Builder builder, @Value("${tayjava.coupon.base-url}") String baseUrl) {
    this.couponRestClient = builder.baseUrl(baseUrl).build();
  }

  public CouponValidationResponse validate(String code) {
    return couponRestClient
        .get()
        .uri("/api/coupons/{code}/validate", code)
        .retrieve()
        .onStatus(
            status -> status.value() == 404,
            (req, res) -> {
              throw new IllegalArgumentException("Coupon not found");
            })
        .onStatus(
            status -> status.is5xxServerError(),
            (req, res) -> {
              throw new IllegalStateException("Coupon service unavailable");
            })
        .body(CouponValidationResponse.class);
  }
}
