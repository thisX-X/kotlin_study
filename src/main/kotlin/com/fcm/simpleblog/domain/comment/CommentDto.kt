package com.fcm.simpleblog.domain.comment

import com.fcm.simpleblog.domain.post.Post

data class CommentSaveReq(
    val title: String,
    val content: String,
    val post: Post,
)

fun CommentSaveReq.toEntity(): Comment {
    return Comment(
        title = this.title,
        content = this.content,
        post = this.post,
    )
}

data class CommentRes(
    val id: Long,
    val title: String,
    val content: String,
    val post: Post,
)