package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import java.sql.ResultSet

class UserDao(
    private val jdbcTemplate: JdbcTemplate,
) {

    private val userMapper: (rs: ResultSet, rowNum: Int) -> User? = { rs, _ ->
        User(
            id = rs.getString("id"),
            name = rs.getString("name"),
            password = rs.getString("password")
        )
    }

    fun add(user: User) {
        jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
            user.id, user.name, user.password)
    }

    fun get(id: String): User {
        return jdbcTemplate.queryForObject("select * from users where id = ?", userMapper, id)
            ?: throw EmptyResultDataAccessException(1)
    }

    fun deleteAll() {
        jdbcTemplate.update("delete from users")
    }

    fun getCount(): Int {
        return jdbcTemplate.queryForObject<Int>("select count(*) from users")
    }

    fun getAll(): List<User> {
        return jdbcTemplate.query("select * from users order by id", userMapper)
    }

}
