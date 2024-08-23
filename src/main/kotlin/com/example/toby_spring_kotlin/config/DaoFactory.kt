package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.account.dao.AccountDao
import com.example.toby_spring_kotlin.message.dao.MessageDao
import com.example.toby_spring_kotlin.user.dao.UserDao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class DaoFactory {

    @Bean
    // 아직 JdbcContext를 적용하지 않은 메소드가 있어서 dataSource()는 유지한다.
    fun userDao(): UserDao = UserDao(jdbcTemplate(), dataSource())

    @Bean
    fun accountDao(): AccountDao = AccountDao(jdbcTemplate())

    @Bean
    fun messageDao(): MessageDao = MessageDao(jdbcTemplate())

    @Bean
    fun jdbcTemplate(): JdbcTemplate = JdbcTemplate(dataSource())

    @Bean
    @Primary
    fun dataSource(): DataSource = DriverManagerDataSource(
        "jdbc:h2:~/toby-spring-kotlin",
        "sa",
        ""
    )

}