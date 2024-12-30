package com.fcm.simpleblog.service

import com.fcm.simpleblog.domain.member.Member
import com.fcm.simpleblog.domain.member.MemberRepository
import com.fcm.simpleblog.domain.member.MemberRes
import com.fcm.simpleblog.domain.member.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional(readOnly = true)
    fun findAll(pageable: Pageable): Page<MemberRes> =
        memberRepository.findMembers(pageable).map {
            it.toDto()
        }


}

