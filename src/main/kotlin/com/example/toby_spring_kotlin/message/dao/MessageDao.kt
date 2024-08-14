package com.example.toby_spring_kotlin.message.dao

import javax.sql.DataSource

class MessageDao(
    private val dataSource: DataSource
)