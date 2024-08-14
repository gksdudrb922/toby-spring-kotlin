package com.example.toby_spring_kotlin.account.dao

import javax.sql.DataSource

class AccountDao(
    private val dataSource: DataSource
)