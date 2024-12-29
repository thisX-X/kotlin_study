package com.fcm.simpleblog.domain.comment

import com.fcm.simpleblog.domain.AuditingEntity
import com.fcm.simpleblog.domain.post.Post
import jakarta.persistence.*

@Entity
@Table(name = "content")
class Comment(

    title: String,
    content: String,
    post: Post,

) : AuditingEntity() {

    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post = post
        protected set

}