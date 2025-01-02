package com.fcm.simpleblog.api

import com.fcm.simpleblog.domain.member.LoginDto
import com.fcm.simpleblog.domain.member.Member
import com.fcm.simpleblog.service.MemberService
import com.fcm.simpleblog.util.CmResDto
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.Serializable

@RequestMapping("/v1")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members")
    fun findAll(@PageableDefault(size = 10) pageable: Pageable, session: HttpSession): CmResDto<*> {
        return CmResDto(HttpStatus.OK, "find All Members", memberService.findAll(pageable))
    }

    @GetMapping("/member/{id}")
    fun findById(@PathVariable id: Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "find Member by Id", memberService.findMemberById(id))
    }

    @DeleteMapping("/member/{id}")
    fun deleteById(@PathVariable id: Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "delete Member By Id", memberService.deleteMember(id))
    }

    @PostMapping("/member")
    fun save(@Valid @RequestBody dto: LoginDto): CmResDto<*> {
        return CmResDto(HttpStatus.OK, "save member", memberService.saveMember(dto))
    }
}