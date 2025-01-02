package com.fcm.simpleblog.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fcm.simpleblog.domain.member.MemberRepository
import com.fcm.simpleblog.util.CmResDto
import com.fcm.simpleblog.util.func.responseData
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity(debug = false)
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val objectMapper: ObjectMapper,
    private val memberRepository: MemberRepository
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf { it.disable() } // CSRF 비활성화
            .httpBasic { it.disable() } // HTTP Basic 인증 비활성화
            .formLogin { it.disable() } // 폼 로그인 비활성화
            .logout { it.disable() } // 로그아웃 비활성화 (선택 사항)
            .cors { it.configurationSource(corsConfigurationSource()) } // CORS 설정
            .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))
                it.accessDeniedHandler(CustomAccessDeniedHandler()) // AccessDeniedHandler 설정
            }
            .authorizeHttpRequests { // 새로운 방식으로 변경
                //it.anyRequest().permitAll() // 모든 요청 허용
                it.requestMatchers("/**").authenticated() // 모든 요청 인증 필요
            }

        return http.build()
    }

    class CustomAuthenticationEntryPoint(
        private val objectMapper: ObjectMapper
    ) : AuthenticationEntryPoint {

        private val log = KotlinLogging.logger {}

        override fun commence(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authException: AuthenticationException
        ) {
            log.info { "Access denied!" }
            //response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)

            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)

            //val cmResDto = CmResDto(HttpStatus.UNAUTHORIZED, "access denied", response)
            //responseData(response, objectMapper.writeValueAsString(cmResDto))

            //response.sendError(HttpServletResponse.SC_FORBIDDEN)
        }
    }

    class CustomFailureHandler : AuthenticationFailureHandler {

        private val log = KotlinLogging.logger {}

        override fun onAuthenticationFailure(
            request: HttpServletRequest,
            response: HttpServletResponse,
            exception: AuthenticationException?
        ) {
            log.info { "로그인 실패" }

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "인증 실패")
        }
    }


    class CustomAccessDeniedHandler : AccessDeniedHandler {

        private val log = KotlinLogging.logger {}

        override fun handle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            accessDeniedException: AccessDeniedException?
        ) {
            log.info { "Access denied!" }
            response.sendError(HttpServletResponse.SC_FORBIDDEN)
        }
    }

    class CustomSuccessHandler : AuthenticationSuccessHandler {

        private val log = KotlinLogging.logger {}

        override fun onAuthenticationSuccess(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication?
        ) {
            log.info { "login Success" }
        }
    }



    @Bean
    fun authenticationFilter(): CustomBasicAuthenticationFilter {
        return CustomBasicAuthenticationFilter(
            authenticationManager = authenticationManager(),
            memberRepository = memberRepository
        )
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun loginFilter(): UsernamePasswordAuthenticationFilter {
        val authenticationFilter = CustomUserNameAuthenticationFilter(objectMapper)
        authenticationFilter.setAuthenticationManager(authenticationManager())
        authenticationFilter.setFilterProcessesUrl("/login")
        authenticationFilter.setAuthenticationFailureHandler(CustomFailureHandler())
        authenticationFilter.setAuthenticationSuccessHandler(CustomSuccessHandler())

        return authenticationFilter
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*") // 모든 Origin 허용
        config.addAllowedMethod("*") // 모든 HTTP 메서드 허용
        config.addAllowedHeader("*") // 모든 헤더 허용
        config.addExposedHeader("Authorization") // Authorization 헤더 노출
        val source = UrlBasedCorsConfigurationSource()
        //source.registerCorsConfiguration("/**", config)
        return source
    }
}