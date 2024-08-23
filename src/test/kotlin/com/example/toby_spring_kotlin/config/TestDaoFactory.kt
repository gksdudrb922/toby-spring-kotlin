package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.user.dao.UserDao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class TestDaoFactory {

    @Bean
    fun testUserDao(): UserDao = UserDao(testJdbcTemplate(), testDataSource())

    @Bean
    fun testJdbcTemplate(): JdbcTemplate = JdbcTemplate(testDataSource())

    @Bean
    fun testDataSource(): DataSource = DriverManagerDataSource(
        "jdbc:h2:~/test",
        "sa",
        ""
    )

}