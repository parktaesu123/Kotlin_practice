package com.example.kotlin_practice.domain.user.domain.repository

import com.example.kotlin_practice.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {
    fun findByAccountId(accountId: String): Optional<User>
}