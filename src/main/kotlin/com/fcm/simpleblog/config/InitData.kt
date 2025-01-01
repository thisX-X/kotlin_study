package com.fcm.simpleblog.config

import com.fcm.simpleblog.domain.member.*
import com.fcm.simpleblog.domain.post.Post
import com.fcm.simpleblog.domain.post.PostRepository
import com.fcm.simpleblog.domain.post.PostSaveReq
import com.fcm.simpleblog.domain.post.toEntity
import io.github.serpro69.kfaker.Faker
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.Transactional

@Configuration
class InitData(

    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository

) {
    val faker = Faker()
    private val log = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    private fun init() {
        //val members = generateMembers(100)
        //memberRepository.saveAll(members)

        //val posts = generatePosts(100)
        //postRepository.saveAll(posts)
    }



    private fun generateMember(): Member =
        LoginDto(
            email = faker.internet.email(),
            password = "1234",
            role = Role.USER
        ).toEntity()

    private fun generateMembers(cnt: Int): MutableList<Member> {
        val members = mutableListOf<Member>()
        for (i in 1..cnt) {
            val member = generateMember()
            log.info { "insert $member" }
            members.add(member)
        }
        return members
    }

    private fun generatePost(): Post = PostSaveReq(
        title = faker.theExpanse.ships(),
        content = faker.quote.matz(),
        memberId = 1,
    ).toEntity()

    private fun generatePosts(cnt: Int): MutableList<Post> {
        val posts = mutableListOf<Post>()
        for (i in 1..cnt) {
            val post = generatePost()
            log.info { "insert $post" }
            posts.add(post)
        }
        return posts
    }

}