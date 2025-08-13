package com.example.kotlin_practice.domain.auth.presentation

import com.example.kotlin_practice.domain.auth.presentation.dto.request.LoginRequest
import com.example.kotlin_practice.domain.auth.presentation.dto.request.SignupRequest
import com.example.kotlin_practice.domain.auth.presentation.dto.response.TokenResponse
import com.example.kotlin_practice.domain.auth.service.LoginService
import com.example.kotlin_practice.domain.auth.service.SignupService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val signupService: SignupService,
    private val loginService: LoginService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid request: SignupRequest) {
        signupService.execute(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): TokenResponse {
        return loginService.execute(request)
    }
}