package com.example.kotlin_practice.domain.auth.presentation.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessExpiredAt: Long,
    val refreshExpiredAt: Long
)
