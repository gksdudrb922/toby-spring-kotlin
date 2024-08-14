package com.example.toby_spring_kotlin.message.dao

import com.example.toby_spring_kotlin.infra.ConnectionMaker

class MessageDao(
    private val connectionMaker: ConnectionMaker
)