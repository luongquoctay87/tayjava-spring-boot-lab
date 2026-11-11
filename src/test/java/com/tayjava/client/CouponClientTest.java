package com.tayjava.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(CouponClient.class)
@TestPropertySource(properties = "tayjava.coupon.base-url=http://127.0.0.1:9")
class CouponClientTest {

  @Autowired private CouponClient couponClient;
  @Autowired private MockRestServiceServer server;

  @Test
  void validateWhenOk() {
    server
        .expect(requestTo("http://127.0.0.1:9/api/coupons/SAVE10/validate"))
        .andRespond(
            withSuccess(
                "{\"valid\":true,\"code\":\"SAVE10\",\"percentOff\":10}",
                MediaType.APPLICATION_JSON));
    CouponValidationResponse body = couponClient.validate("SAVE10");
    assertThat(body.valid()).isTrue();
  }
}
