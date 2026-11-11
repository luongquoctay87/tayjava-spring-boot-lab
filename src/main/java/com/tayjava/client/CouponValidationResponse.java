package com.tayjava.client;

public record CouponValidationResponse(boolean valid, String code, int percentOff) {}
