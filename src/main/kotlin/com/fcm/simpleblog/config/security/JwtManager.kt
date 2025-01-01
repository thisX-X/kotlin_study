package com.fcm.simpleblog.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import mu.KotlinLogging
import java.security.Principal
import java.util.*

class JwtManager {
    private val log = KotlinLogging.logger {}

    private val secretKey: String = "asdfasdf"
    private val claimEmail = "email"
    private val claimPassword = "password"
    private val expireTime = 1000 * 60 * 60
    val jwtHeader = "Authorization"

    fun generateAccessToken(principalDetail: PrincipalDetails): String {
        return JWT.create()
            .withSubject(principalDetail.username)
            .withExpiresAt(Date(System.nanoTime() + expireTime))
            .withClaim(claimEmail, principalDetail.username)
            .withClaim(claimPassword, principalDetail.password)
            .sign(Algorithm.HMAC512(secretKey))
    }

    fun getMemberEmail(token: String): String? {
        return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
            .getClaim(claimEmail).asString()
    }

}