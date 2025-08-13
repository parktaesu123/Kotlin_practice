package com.example.kotlin_practice.global.error.exeption

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
    val status: Int,
    val message: String
) {
    //jwt
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),

    //user
    USER_NOT_FOUND(404, "User not found"),

    //feed
    FEED_NOT_FOUND(404, "Feed not found"),

    // general
    BAD_REQUEST(400, "프론트 탓"),
    INTERNAL_SERVER_ERROR(500, "서버 탓");
}