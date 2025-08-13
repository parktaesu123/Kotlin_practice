package com.example.kotlin_practice.domain.auth.service

import com.example.kotlin_practice.domain.auth.exception.PasswordMisMatchException
import com.example.kotlin_practice.domain.auth.presentation.dto.request.LoginRequest
import com.example.kotlin_practice.domain.auth.presentation.dto.response.TokenResponse
import com.example.kotlin_practice.domain.user.service.facade.UserFacade
import com.example.kotlin_practice.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val userFacade: UserFacade
) {
    fun execute(request: LoginRequest): TokenResponse {
        val user = userFacade.currentUser()

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordMisMatchException.EXCEPTION
        }

        return jwtTokenProvider.receiveToken(request.accountId)
    }
}