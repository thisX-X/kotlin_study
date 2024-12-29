package com.fcm.simpleblog.service

import com.fcm.simpleblog.domain.member.Member
import com.fcm.simpleblog.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): MutableList<Member>  =  memberRepository.findAll()

}