package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.user.domain.User
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class UserDao(
    private val connectionMaker: ConnectionMaker
) {

    fun add(user: User) {
        val c: Connection = connectionMaker.makeConnection()

        val ps: PreparedStatement = c.prepareStatement("insert into users(id, name, password) values(?,?,?)")
        ps.setString(1, user.id)
        ps.setString(2, user.name)
        ps.setString(3, user.password)

        ps.executeUpdate()

        ps.close()
        c.close()
    }

    fun get(id: String): User {
        val c: Connection = connectionMaker.makeConnection()
        val ps: PreparedStatement = c.prepareStatement("select * from users where id = ?")
        ps.setString(1, id)

        val rs: ResultSet = ps.executeQuery()
        rs.next()
        val user = User()
        user.id = rs.getString("id")
        user.name = rs.getString("name")
        user.password = rs.getString("password")

        rs.close()
        ps.close()
        c.close()

        return user
    }

}
