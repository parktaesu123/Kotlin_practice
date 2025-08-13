package com.example.kotlin_practice.domain.auth.presentation.dto.request

data class SignupRequest(
    val userName: String,
    val accountId: String,
    val password: String
)
