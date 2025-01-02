package com.fcm.simpleblog.api

import com.fcm.simpleblog.domain.post.PostSaveReq
import com.fcm.simpleblog.service.PostService
import com.fcm.simpleblog.util.CmResDto
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1")
@RestController
class PostController(
    private val postService: PostService
) {

    @GetMapping("/posts")
    fun findPosts(@PageableDefault(size = 10) pageable: Pageable): CmResDto<*> {
        return CmResDto(HttpStatus.OK, "find Posts", postService.findPosts(pageable))
    }

    @GetMapping("/posts/{id}")
    fun findById(@PathVariable id: Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "find post by id", postService.findPostById(id))
    }

    @DeleteMapping("/posts/{id}")
    fun deleteById(@PathVariable id: Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "delete post by id", postService.deletePost(id))
    }

    @PostMapping("/posts")
    fun save(@Valid @RequestBody dto: PostSaveReq): CmResDto<*> {
        return CmResDto(HttpStatus.OK, "save post", postService.savePost(dto))
    }

}