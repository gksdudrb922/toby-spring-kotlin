package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.account.dao.AccountDao
import com.example.toby_spring_kotlin.message.dao.MessageDao
import com.example.toby_spring_kotlin.infra.JdbcContext
import com.example.toby_spring_kotlin.user.dao.UserDao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class DaoFactory {

    @Bean
    // 아직 JdbcContext를 적용하지 않은 메소드가 있어서 dataSource()는 유지한다.
    fun userDao(): UserDao = UserDao(jdbcContext(), dataSource())

    @Bean
    fun accountDao(): AccountDao = AccountDao(jdbcContext())

    @Bean
    fun messageDao(): MessageDao = MessageDao(jdbcContext())

    @Bean
    fun jdbcContext(): JdbcContext = JdbcContext(dataSource())

    @Bean
    @Primary
    fun dataSource(): DataSource = DriverManagerDataSource(
        "jdbc:h2:~/toby-spring-kotlin",
        "sa",
        ""
    )

}