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
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.util.*


@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository,
    private val customUserDetailsService: CustomUserDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun createAccessToken(accountId: String): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(accountId)
            .claim("type", "access")
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtProperties.accessExpiration * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secretKey)
            .compact()
    }

    @Transactional
    fun createRefreshToken(accountId: String): String {
        val now = Date()
        val refreshToken = Jwts.builder()
            .claim("type", "refresh")
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtProperties.refreshExpiration * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secretKey)
            .compact()

        // Kotlin 스타일 생성자 사용
        val entity = RefreshToken(
            accountId = accountId,
            token = refreshToken,
            timeToLive = jwtProperties.refreshExpiration
        )
        refreshTokenRepository.save(entity)

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
            Jwts.parser()
                .setSigningKey(jwtProperties.secretKey)
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
            accessExpiredAt = now.time + jwtProperties.accessExpiration * 1000,
            refreshExpiredAt = now.time + jwtProperties.refreshExpiration * 1000
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