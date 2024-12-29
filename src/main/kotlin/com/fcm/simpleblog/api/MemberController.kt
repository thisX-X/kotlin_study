package com.fcm.simpleblog.api

import com.fcm.simpleblog.domain.member.Member
import com.fcm.simpleblog.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members")
    fun findAll(): MutableList<Member> {
        return memberService.findAll()
    }
}