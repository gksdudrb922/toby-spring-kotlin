package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class UserDao(
    private val jdbcTemplate: JdbcTemplate,
) {

    fun add(user: User) {
        jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
            user.id, user.name, user.password)
    }

    fun get(id: String): User {
        return jdbcTemplate.queryForObject(
            "select * from users where id = ?",
            { rs, _ ->
                User(
                    id = rs.getString("id"),
                    name = rs.getString("name"),
                    password = rs.getString("password")
                )
            },
            id,
        ) ?: throw EmptyResultDataAccessException(1)
    }

    fun deleteAll() {
        jdbcTemplate.update("delete from users")
    }

    fun getCount(): Int {
        return jdbcTemplate.queryForObject<Int>("select count(*) from users")
    }

    fun getAll(): List<User> {
        return jdbcTemplate.query(
            "select * from users order by id"
        ) { rs, _ ->
            User(
                id = rs.getString("id"),
                name = rs.getString("name"),
                password = rs.getString("password")
            )
        }
    }

}
