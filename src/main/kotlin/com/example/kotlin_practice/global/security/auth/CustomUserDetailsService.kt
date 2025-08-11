package com.example.kotlin_practice.global.security.auth

import com.example.kotlin_practice.domain.user.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(accountId: String): UserDetails {
        val user = userRepository.findByAccountId(accountId)
            .orElseThrow { UsernameNotFoundException("User Not Found") }
        return CustomUserDetails(user)
    }
}