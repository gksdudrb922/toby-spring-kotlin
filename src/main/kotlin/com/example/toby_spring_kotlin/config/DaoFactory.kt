package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.dao.UserDaoJdbc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class DaoFactory {

    @Bean
    fun userDao(): UserDao = UserDaoJdbc(jdbcTemplate())

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