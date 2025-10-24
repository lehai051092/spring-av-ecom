package com.ecommerce.project.security.response;

public record LoginResponse(
        UserInfoResponse userInfoResponse,
        String jwtCookie
) {
}
