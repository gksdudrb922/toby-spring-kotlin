package com.example.toby_spring_kotlin.account.dao

import com.example.toby_spring_kotlin.infra.ConnectionMaker

class AccountDao(
    private val connectionMaker: ConnectionMaker
)