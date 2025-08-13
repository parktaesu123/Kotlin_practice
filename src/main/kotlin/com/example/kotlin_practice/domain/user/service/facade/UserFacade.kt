package com.example.kotlin_practice.domain.user.service.facade

import com.example.kotlin_practice.domain.user.domain.User
import com.example.kotlin_practice.domain.user.domain.repository.UserRepository
import com.example.kotlin_practice.domain.user.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {
    fun currentUser(): User {
        val accountId: String = SecurityContextHolder.getContext().authentication.name

        return userRepository.findByAccountId(accountId)
            .orElseThrow { UserNotFoundException.EXCEPTION }
    }
}