package com.fcm.simpleblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleBlogApplication

fun main(args: Array<String>) {
    runApplication<SimpleBlogApplication>(*args)
}

/**
 * dev: aws ec2(프리티어) + s3 + codedeploy + github action
 *
 * back : spring boot + kotlin + jpa
 *
 * front : react + typescript + zustand
 */