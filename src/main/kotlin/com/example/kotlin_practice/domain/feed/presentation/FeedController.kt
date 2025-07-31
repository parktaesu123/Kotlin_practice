package com.example.kotlin_practice.domain.feed.presentation

import com.example.kotlin_practice.domain.feed.presentation.dto.FeedRequest
import com.example.kotlin_practice.domain.feed.service.CreateFeedService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feed")
class FeedController(
    private val createFeedService: CreateFeedService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createFeed(@RequestBody @Valid request: FeedRequest) {
        createFeedService.execute(request)
    }
}