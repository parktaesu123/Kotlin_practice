package com.example.kotlin_practice.domain.auth.service

import com.example.kotlin_practice.domain.auth.presentation.dto.request.SignupRequest
import com.example.kotlin_practice.domain.user.domain.User
import com.example.kotlin_practice.domain.user.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignupService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun execute(request: SignupRequest) {
        userRepository.save(
            User(
                accountId = request.accountId,
                password = passwordEncoder.encode(request.password),
                userName = request.userName
            )
        )
    }
}