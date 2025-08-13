package com.example.kotlin_practice.domain.auth.domain.repository

import com.example.kotlin_practice.domain.auth.domain.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {
    fun findByToken(token: String): Optional<RefreshToken>
}