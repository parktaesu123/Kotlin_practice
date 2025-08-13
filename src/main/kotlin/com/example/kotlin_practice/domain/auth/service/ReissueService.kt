package com.example.kotlin_practice.domain.auth.service

import com.example.kotlin_practice.domain.auth.domain.repository.RefreshTokenRepository
import com.example.kotlin_practice.domain.auth.exception.RefreshTokenNotFoundException
import com.example.kotlin_practice.domain.auth.presentation.dto.request.RefreshTokenRequest
import com.example.kotlin_practice.domain.auth.presentation.dto.response.TokenResponse
import com.example.kotlin_practice.domain.user.service.facade.UserFacade
import com.example.kotlin_practice.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class ReissueService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userFacade: UserFacade
) {
    fun execute(request: RefreshTokenRequest): TokenResponse {
        userFacade.currentUser()

        val refreshToken = refreshTokenRepository.findByToken(request.refreshToken)
            .orElseThrow { RefreshTokenNotFoundException.EXCEPTION }

        return jwtTokenProvider.receiveToken(refreshToken.accountId)
    }
}