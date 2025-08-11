package com.example.kotlin_practice.global.error.exeption

abstract class KotlinException(
    val errorCode: ErrorCode
) : RuntimeException()