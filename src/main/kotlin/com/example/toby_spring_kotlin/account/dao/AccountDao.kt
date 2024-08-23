package com.example.toby_spring_kotlin.account.dao

import com.example.toby_spring_kotlin.infra.JdbcContext

class AccountDao(
    private val jdbcContext: JdbcContext,
)