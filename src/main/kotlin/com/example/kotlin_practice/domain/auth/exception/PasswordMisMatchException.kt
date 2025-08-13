package com.example.kotlin_practice.domain.auth.exception

import com.example.kotlin_practice.global.error.exeption.ErrorCode
import com.example.kotlin_practice.global.error.exeption.KotlinException

class PasswordMisMatchException: KotlinException(ErrorCode.PASSWORD_MISMATCH) {
    companion object {
        val EXCEPTION = PasswordMisMatchException()
    }
}