package com.example.kotlin_practice.domain.feed.domain.repository

import com.example.kotlin_practice.domain.feed.domain.Feed
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository: JpaRepository<Feed, Long> {
}