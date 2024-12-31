package com.fcm.simpleblog.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fcm.simpleblog.domain.member.LoginDto
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource

class CustomUserNameAuthenticationFilter(
    private val objectMapper: ObjectMapper,
) : UsernamePasswordAuthenticationFilter() {
    private val log = KotlinLogging.logger { }
    private val jwtmanager = JwtManager()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {
        log.info { "login 요청 출력" }
        //request.getAttribute("email")

        lateinit var loginDto: LoginDto

        try {
            loginDto = objectMapper.readValue(request?.inputStream, LoginDto::class.java)
            log.info { "loginDto : $loginDto" }
        } catch(e: Exception) {
            log.error { "loginFilter: 로그인 요청 Dto 생성 중 실패   $e" }
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)

        return this.authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        log.info { "로그인 완료 후 JWT 토근 생성 후 response" }

        val principalDetails = authResult?.principal as PrincipalDetails

        val jwtToken = jwtmanager.generateAccessToken(principalDetails)

        response?.addHeader("Authorization", "Bearer " + jwtToken)
    }
}