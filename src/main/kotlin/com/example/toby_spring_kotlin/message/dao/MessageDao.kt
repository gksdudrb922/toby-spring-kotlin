package com.example.toby_spring_kotlin.message.dao

import org.springframework.jdbc.core.JdbcTemplate

class MessageDao(
    private val jdbcTemplate: JdbcTemplate,
)