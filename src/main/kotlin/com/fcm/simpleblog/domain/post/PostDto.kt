package com.fcm.simpleblog.domain.post

import com.fcm.simpleblog.domain.member.Member

data class PostSaveReq (
    val title: String,
    val content: String,
    val memberId: Long
)

fun PostSaveReq.toEntity(): Post {
    return Post(
        title = this.title,
        content = this.content,
        member = Member.createFakeMember(this.memberId)
    )
}

data class PostRes (
    val id: Long,
    val title: String,
    val content: String,
    val memberId: Long
)