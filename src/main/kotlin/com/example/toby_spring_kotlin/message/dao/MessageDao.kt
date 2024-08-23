package com.example.toby_spring_kotlin.message.dao

import com.example.toby_spring_kotlin.infra.JdbcContext

class MessageDao(
    private val jdbcContext: JdbcContext,
)