package com.example.toby_spring_kotlin.config

import com.example.toby_spring_kotlin.infra.CountingDataSource
import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.dao.UserDaoJdbc
import com.example.toby_spring_kotlin.user.service.DefaultUserLevelUpgradePolicy
import com.example.toby_spring_kotlin.user.service.UserLevelUpgradePolicy
import com.example.toby_spring_kotlin.user.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class TestBeanConfig {

    @Bean
    fun testUserService(): UserService = UserService(testUserLevelUpgradePolicy(), testUserDao(), testDataSource())

    @Bean
    fun testUserLevelUpgradePolicy(): UserLevelUpgradePolicy = DefaultUserLevelUpgradePolicy(testUserDao())

    @Bean
    fun testUserDao(): UserDao = UserDaoJdbc(testJdbcTemplate())

    @Bean
    fun testJdbcTemplate(): JdbcTemplate = JdbcTemplate(testDataSource())

    @Bean
    fun testDataSource(): DataSource = DriverManagerDataSource(
        "jdbc:h2:~/test",
        "sa",
        ""
    )

    @Bean
    fun testUserDaoCounting(): UserDao = UserDaoJdbc(testJdbcTemplateCounting())

    @Bean
    fun testJdbcTemplateCounting(): JdbcTemplate = JdbcTemplate(testDataSourceCounting())

    @Bean
    fun testDataSourceCounting(): CountingDataSource = CountingDataSource(testRealDataSource())

    @Bean
    fun testRealDataSource(): DataSource = DriverManagerDataSource(
        "jdbc:h2:~/test",
        "sa",
        ""
    )

}