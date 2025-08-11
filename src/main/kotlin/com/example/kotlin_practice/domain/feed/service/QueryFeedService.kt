package com.example.kotlin_practice.domain.feed.service

import com.example.kotlin_practice.domain.feed.domain.repository.FeedRepository
import com.example.kotlin_practice.domain.feed.presentation.dto.response.FeedResponse
import com.example.kotlin_practice.domain.feed.service.facade.FeedFacade
import org.springframework.stereotype.Service

@Service
class QueryFeedService(
    private val feedRepository: FeedRepository,
    private val feedFacade: FeedFacade
) {
    fun execute(feedId: Long): FeedResponse {

        val feed = feedFacade.getFeed(feedId)

        return FeedResponse(
            id = feed.id,
            title = feed.title,
            content = feed.content
        )
    }
}