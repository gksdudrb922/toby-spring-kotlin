package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.user.domain.User
import java.sql.Connection
import java.sql.PreparedStatement

class AddStatement(
    private val user: User,
): StatementStrategy {

    override fun makePreparedStatement(c: Connection): PreparedStatement {
        val ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)")
        ps.setString(1, user.id)
        ps.setString(2, user.name)
        ps.setString(3, user.password)
        return ps
    }

}