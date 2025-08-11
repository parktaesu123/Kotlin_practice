package com.example.kotlin_practice.domain.auth.domain.repository

import com.example.kotlin_practice.domain.auth.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {
}