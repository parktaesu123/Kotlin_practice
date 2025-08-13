package com.example.kotlin_practice.domain.auth.presentation.dto.request

data class LoginRequest(
    val accountId: String,
    val password: String
)
