package com.fcm.simpleblog.service

import com.fcm.simpleblog.domain.post.Post
import com.fcm.simpleblog.domain.post.PostRepository
import com.fcm.simpleblog.domain.post.PostRes
import com.fcm.simpleblog.domain.post.toDto
import com.fcm.simpleblog.util.CmResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
) {
    @Transactional(readOnly = true)
    fun findPosts(pageable: Pageable): Page<PostRes> {
        return postRepository.findPosts(pageable).map { it.toDto() }
    }
}