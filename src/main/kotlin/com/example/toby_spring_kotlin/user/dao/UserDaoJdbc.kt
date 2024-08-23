package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import java.sql.ResultSet

class UserDaoJdbc(
    private val jdbcTemplate: JdbcTemplate,
) : UserDao {

    private val userMapper: (rs: ResultSet, rowNum: Int) -> User? = { rs, _ ->
        User(
            id = rs.getString("id"),
            name = rs.getString("name"),
            password = rs.getString("password")
        )
    }

    override fun add(user: User) {
        jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
            user.id, user.name, user.password)
    }

    override fun get(id: String): User {
        return jdbcTemplate.queryForObject("select * from users where id = ?", userMapper, id)
            ?: throw EmptyResultDataAccessException(1)
    }

    override fun deleteAll() {
        jdbcTemplate.update("delete from users")
    }

    override fun getCount(): Int {
        return jdbcTemplate.queryForObject<Int>("select count(*) from users")
    }

    override fun getAll(): List<User> {
        return jdbcTemplate.query("select * from users order by id", userMapper)
    }

}
