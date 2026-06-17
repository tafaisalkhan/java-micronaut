package com.micronet.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record LoginResponse(String token, String username, String message) {
}
