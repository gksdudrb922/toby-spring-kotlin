package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.infra.ConnectionMaker
import com.example.toby_spring_kotlin.infra.CountingConnectionMaker
import com.example.toby_spring_kotlin.infra.DConnectionMaker
import com.example.toby_spring_kotlin.user.dao.UserDao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CountingDaoFactory {

    @Bean
    fun userDaoCounting(): UserDao = UserDao(connectionMakerCounting())

    @Bean
    fun connectionMakerCounting(): ConnectionMaker = CountingConnectionMaker(realConnectionMaker())

    @Bean
    fun realConnectionMaker(): ConnectionMaker = DConnectionMaker()

}