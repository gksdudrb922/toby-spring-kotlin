package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.infra.CountingDataSource
import com.example.toby_spring_kotlin.user.dao.UserDao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class CountingDaoFactory {

    @Bean
    fun userDaoCounting(): UserDao = UserDao(jdbcTemplateCounting())

    @Bean
    fun jdbcTemplateCounting(): JdbcTemplate = JdbcTemplate(dataSourceCounting())

    @Bean
    fun dataSourceCounting(): CountingDataSource = CountingDataSource(realDataSource())

    @Bean
    fun realDataSource(): DataSource = DriverManagerDataSource(
        "jdbc:h2:~/toby-spring-kotlin",
        "sa",
        ""
    )

}