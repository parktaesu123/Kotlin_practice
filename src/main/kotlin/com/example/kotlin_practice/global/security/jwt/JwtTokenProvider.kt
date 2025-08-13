package com.example.kotlin_practice.global.security.jwt

import com.example.kotlin_practice.domain.auth.domain.RefreshToken
import com.example.kotlin_practice.domain.auth.domain.repository.RefreshTokenRepository
import com.example.kotlin_practice.domain.auth.exception.ExpiredTokenException
import com.example.kotlin_practice.domain.auth.exception.InvalidTokenException
import com.example.kotlin_practice.domain.auth.presentation.dto.response.TokenResponse
import com.example.kotlin_practice.domain.user.domain.repository.UserRepository
import com.example.kotlin_practice.domain.user.exception.UserNotFoundException
import com.example.kotlin_practice.global.security.auth.CustomUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*


@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository,
    private val customUserDetailsService: CustomUserDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    companion object {
        private const val TYPE_CLAIM = "type"
        private const val ACCESS_TYPE = "access"
        private const val REFRESH_TYPE = "refresh"
        private const val MILLISECONDS = 1000
    }

    private val secretKey: Key = try {
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.secretKey))
    } catch (e: IllegalArgumentException) {
        Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    private fun generateToken(accountId: String, type: String, expirationSeconds: Long): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(accountId)
            .claim(TYPE_CLAIM, type)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationSeconds * MILLISECONDS))
            .signWith(secretKey)
            .compact()
    }

    fun createAccessToken(accountId: String): String =
        generateToken(accountId, ACCESS_TYPE, jwtProperties.accessExpiration)

    @Transactional
    fun createRefreshToken(accountId: String): String {
        val refreshToken = generateToken(accountId, REFRESH_TYPE, jwtProperties.refreshExpiration)

        refreshTokenRepository.save(
            RefreshToken(
                accountId = accountId,
                token = refreshToken,
                timeToLive = jwtProperties.refreshExpiration
            )
        )
        return refreshToken
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val userDetails: UserDetails =
            customUserDetailsService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getClaims(token: String): Claims =
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException.EXCEPTION
        } catch (e: Exception) {
            throw InvalidTokenException.EXCEPTION
        }

    fun receiveToken(accountId: String): TokenResponse {
        val now = Date()

        userRepository.findByAccountId(accountId)
            .orElseThrow { UserNotFoundException.EXCEPTION }

        return TokenResponse(
            accessToken = createAccessToken(accountId),
            refreshToken = createRefreshToken(accountId),
            accessExpiredAt = now.time + jwtProperties.accessExpiration * MILLISECONDS,
            refreshExpiredAt = now.time + jwtProperties.refreshExpiration * MILLISECONDS
        )
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(jwtProperties.header) ?: return null
        val prefix = jwtProperties.prefix

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(prefix)) {
            val token = bearerToken.substring(prefix.length).trim()
            if (token.isNotEmpty()) return token
        }
        return null
    }
}