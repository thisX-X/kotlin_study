package com.fcm.simpleblog.domain.member

import com.fcm.simpleblog.domain.AuditingEntity
import jakarta.persistence.*

@Entity
class Member(

    email: String,
    password: String,
    role: Role

) : AuditingEntity() {


    @Column(name = "email", nullable = false)
    var email: String = email
        protected set

    @Column(name = "password", nullable = false)
    var password: String = password
        protected set

    @Enumerated(EnumType.STRING)
    var role: Role = role
        protected set

    override fun toString(): String {
        return "Member(email='$email', password='$password', role=$role)"
    }


}

enum class Role {
    USER, ADMIN
}