package com.fcm.simpleblog.domain.member

import jakarta.validation.constraints.NotNull

/**
 * dto <-> entity 간의 매핑에서 2가지 스타일이 존재
 * 1. 각 dto, entity에 책임 할당
 * 2. entitymapper 인터페이스 또는 클래스를 만들어서 역할을 담당하게 지정
 */

data class MemberSaveReq(
    @field:NotNull(message = "require email")
    val email: String?,
    val password: String?,
    val role: Role?
)

fun MemberSaveReq.toEntity(): Member{
    return Member(
        email = this.email ?: "",
        password = this.password ?: "",
        role = this.role ?: Role.USER,
    )
}

data class MemberRes(
    val id: Long,
    val email: String,
    val password: String,
    val role: Role
)