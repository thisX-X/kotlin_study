package com.fcm.simpleblog.service

import com.fcm.simpleblog.config.security.PrincipalDetails
import com.fcm.simpleblog.domain.member.MemberRepository
import com.fcm.simpleblog.exception.MemberNotFoundException
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository,
) : UserDetailsService {

    val log = KotlinLogging.logger {}

    override fun loadUserByUsername(email: String): UserDetails {
        log.info { "loadUserByUsername 호출 " }

        val member = memberRepository.findMemberByEmail(email)

        return PrincipalDetails(member)

    }
}