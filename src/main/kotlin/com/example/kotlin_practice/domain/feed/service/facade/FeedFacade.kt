package com.example.kotlin_practice.domain.feed.service.facade

import com.example.kotlin_practice.domain.feed.domain.Feed
import com.example.kotlin_practice.domain.feed.domain.repository.FeedRepository
import com.example.kotlin_practice.domain.feed.exception.FeedNotFoundException
import org.springframework.stereotype.Component

@Component
class FeedFacade(
    private val feedRepository: FeedRepository
) {
    fun getFeed(feedId: Long): Feed {
        return feedRepository.findById(feedId)
            .orElseThrow { FeedNotFoundException.EXCEPTION }
    }
}