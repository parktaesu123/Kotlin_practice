package com.example.kotlin_practice.domain.feed.presentation

import com.example.kotlin_practice.domain.feed.presentation.dto.request.FeedRequest
import com.example.kotlin_practice.domain.feed.presentation.dto.response.FeedResponse
import com.example.kotlin_practice.domain.feed.service.CreateFeedService
import com.example.kotlin_practice.domain.feed.service.QueryFeedService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feed")
class FeedController(
    private val createFeedService: CreateFeedService,
    private val queryFeedService: QueryFeedService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createFeed(@RequestBody @Valid request: FeedRequest) {
        createFeedService.execute(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun queryFeed(@PathVariable id: Long): FeedResponse {
        return queryFeedService.execute(id)
    }
}