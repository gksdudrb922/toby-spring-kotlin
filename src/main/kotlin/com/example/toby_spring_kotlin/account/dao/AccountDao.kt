package com.example.toby_spring_kotlin.account.dao

import org.springframework.jdbc.core.JdbcTemplate

class AccountDao(
    private val jdbcTemplate: JdbcTemplate,
)