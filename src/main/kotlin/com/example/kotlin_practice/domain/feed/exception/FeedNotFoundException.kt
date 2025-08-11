package com.example.kotlin_practice.domain.feed.exception

import com.example.kotlin_practice.global.error.exeption.ErrorCode
import com.example.kotlin_practice.global.error.exeption.KotlinException

class FeedNotFoundException: KotlinException
    (ErrorCode.FEED_NOT_FOUND)  {
        companion object {
            @JvmField
            val EXCEPTION = FeedNotFoundException()
        }
}