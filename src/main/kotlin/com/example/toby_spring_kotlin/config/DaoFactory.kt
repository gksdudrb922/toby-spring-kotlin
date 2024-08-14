package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.account.dao.AccountDao
import com.example.toby_spring_kotlin.message.dao.MessageDao
import com.example.toby_spring_kotlin.user.dao.UserDao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class DaoFactory {

    @Bean
    fun userDao(): UserDao = UserDao(dataSource())

    @Bean
    fun accountDao(): AccountDao = AccountDao(dataSource())

    @Bean
    fun messageDao(): MessageDao = MessageDao(dataSource())

    @Bean
    @Primary
    fun dataSource(): DataSource = DriverManagerDataSource(
        "jdbc:h2:~/test",
        "sa",
        ""
    )

}