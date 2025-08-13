package com.example.kotlin_practice.domain.auth.exception

import com.example.kotlin_practice.global.error.exeption.ErrorCode
import com.example.kotlin_practice.global.error.exeption.KotlinException

class ExpiredTokenException : KotlinException(ErrorCode.EXPIRED_TOKEN) {
    companion object {
        val EXCEPTION = ExpiredTokenException()
    }
}