package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.account.dao.AccountDao
import com.example.toby_spring_kotlin.infra.ConnectionMaker
import com.example.toby_spring_kotlin.infra.DConnectionMaker
import com.example.toby_spring_kotlin.message.dao.MessageDao
import com.example.toby_spring_kotlin.user.dao.UserDao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DaoFactory {

    @Bean
    fun userDao(): UserDao = UserDao(connectionMaker())

    @Bean
    fun accountDao(): AccountDao = AccountDao(connectionMaker())

    @Bean
    fun messageDao(): MessageDao = MessageDao(connectionMaker())

    @Bean
    fun connectionMaker(): ConnectionMaker = DConnectionMaker()

}