package com.example.kotlin_practice.domain.feed.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class FeedRequest(
    @field:NotBlank(message = "제목을 입력하세요")
    @field:Size(max = 20, message = "제목은 20자 이내로 작성해주세요")
    var title: String,

    @field:NotBlank(message = "내용을 입력하세요")
    @field:Size(max = 200, message = "200자 이내로 작성해주세요")
    var content: String
)