package com.example.kotlin_practice.domain.user.domain.repository

import com.example.kotlin_practice.domain.user.domain.User
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository: CrudRepository<User, Long> {
    fun findByAccountId(accountId: String): Optional<User>
}