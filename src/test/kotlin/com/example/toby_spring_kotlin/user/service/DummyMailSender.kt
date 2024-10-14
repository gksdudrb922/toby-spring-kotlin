package com.example.toby_spring_kotlin.user.service

import org.slf4j.LoggerFactory
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

class DummyMailSender : MailSender {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun send(vararg simpleMessages: SimpleMailMessage?) {
        log.info("send message: {}", simpleMessages)
    }

}