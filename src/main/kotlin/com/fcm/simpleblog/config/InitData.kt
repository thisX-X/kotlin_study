package com.fcm.simpleblog.config

import com.fcm.simpleblog.domain.member.*
import io.github.serpro69.kfaker.Faker
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class InitData(

    private val memberRepository: MemberRepository

) {
    val faker = Faker()
    private val log = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    private fun init() {

        val members = mutableListOf<Member>()

        for (i in 1..100) {
            val member = generateMember()
            log.info { "insert $member" }
            members.add(member)
        }

        memberRepository.saveAll(members)
    }



    private fun generateMember(): Member =
        MemberSaveReq(
            email = faker.internet.email(),
            password = "1234",
            role = Role.USER
        ).toEntity()


}