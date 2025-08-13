package com.example.kotlin_practice.domain.auth.exception

import com.example.kotlin_practice.global.error.exeption.ErrorCode
import com.example.kotlin_practice.global.error.exeption.KotlinException

class RefreshTokenNotFoundException: KotlinException(ErrorCode.REFRESH_TOKEN_NOT_FOUND) {
    companion object {
        val EXCEPTION = RefreshTokenNotFoundException()
    }
}