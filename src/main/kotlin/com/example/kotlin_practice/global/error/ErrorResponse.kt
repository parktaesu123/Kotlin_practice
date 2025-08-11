package com.example.kotlin_practice.global.error

import com.example.kotlin_practice.global.error.exeption.ErrorCode
import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val status: Int,
    val timestamp: LocalDateTime,
    val description: String?
) {
    companion object {
        fun of(errorCode: ErrorCode, description: String?): ErrorResponse =
            ErrorResponse(
                message = errorCode.message,
                status = errorCode.status,
                timestamp = LocalDateTime.now(),
                description = description
            )

        fun of(statusCode: Int, description: String?): ErrorResponse =
            ErrorResponse(
                message = description ?: "",
                status = statusCode,
                timestamp = LocalDateTime.now(),
                description = description
            )
    }
}
