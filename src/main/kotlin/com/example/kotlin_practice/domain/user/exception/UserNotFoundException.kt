package com.example.kotlin_practice.domain.user.exception

import com.example.kotlin_practice.global.error.exeption.ErrorCode
import com.example.kotlin_practice.global.error.exeption.KotlinException

class UserNotFoundException: KotlinException(
    ErrorCode.USER_NOT_FOUND
) {
    companion object {
        @JvmField
        val EXCEPTION = UserNotFoundException()
    }
}