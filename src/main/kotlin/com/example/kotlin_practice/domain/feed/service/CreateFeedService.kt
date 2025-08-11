package com.example.kotlin_practice.domain.feed.service

import com.example.kotlin_practice.domain.feed.domain.Feed
import com.example.kotlin_practice.domain.feed.domain.repository.FeedRepository
import com.example.kotlin_practice.domain.feed.presentation.dto.request.FeedRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CreateFeedService(
    private val feedRepository: FeedRepository
) {

    @Transactional
    fun execute(request: FeedRequest) {
        feedRepository.save(
            Feed(
                title = request.title,
                content = request.content
            )
        )
    }
}