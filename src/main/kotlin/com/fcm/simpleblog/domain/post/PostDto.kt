package com.fcm.simpleblog.domain.post

import com.fcm.simpleblog.domain.member.Member
import com.fcm.simpleblog.domain.member.MemberRes
import jakarta.validation.constraints.NotNull

data class PostSaveReq (
    @field:NotNull(message = "require title")
    val title: String?,
    val content: String?,
    @field:NotNull(message = "require memberId")
    val memberId: Long?
)

fun PostSaveReq.toEntity(): Post {
    return Post(
        title = this.title ?: "",
        content = this.content ?: "",
        member = Member.createFakeMember(this.memberId!!)
    )
}

data class PostRes (
    val id: Long,
    val title: String,
    val content: String,
    val member: MemberRes
)