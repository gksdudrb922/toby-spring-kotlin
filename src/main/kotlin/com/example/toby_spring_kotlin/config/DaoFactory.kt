package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.account.dao.AccountDao
import com.example.toby_spring_kotlin.infra.ConnectionMaker
import com.example.toby_spring_kotlin.infra.DConnectionMaker
import com.example.toby_spring_kotlin.message.dao.MessageDao
import com.example.toby_spring_kotlin.user.dao.UserDao

class DaoFactory {

    fun userDao(): UserDao = UserDao(connectionMaker())

    fun accountDao(): AccountDao = AccountDao(connectionMaker())

    fun messageDao(): MessageDao = MessageDao(connectionMaker())

    private fun connectionMaker(): ConnectionMaker = DConnectionMaker()

}