package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.infra.JdbcContext
import com.example.toby_spring_kotlin.infra.StatementStrategy
import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.dao.EmptyResultDataAccessException
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource

class UserDao(
    private val jdbcContext: JdbcContext,
    private val dataSource: DataSource,
) {

    fun add(user: User) {
        jdbcContext.workWithStatementStrategy(object : StatementStrategy {
            override fun makePreparedStatement(c: Connection): PreparedStatement {
                val ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)")
                ps.setString(1, user.id)
                ps.setString(2, user.name)
                ps.setString(3, user.password)
                return ps
            }
        })
    }

    fun get(id: String): User {
        var c: Connection? = null
        var ps: PreparedStatement? = null
        var rs: ResultSet? = null
        var user: User? = null

        try {
            c = dataSource.connection
            ps = c.prepareStatement("select * from users where id = ?")
            ps.setString(1, id)

            rs = ps.executeQuery()
            if (rs.next()) {
                user = User(
                    id = rs.getString("id"),
                    name = rs.getString("name"),
                    password = rs.getString("password")
                )
            }
        } catch (e: SQLException) {
            throw e
        } finally {
            if (rs != null) {
                try {
                    rs.close()
                } catch (_: SQLException) {}
            }
            if (ps != null) {
                try {
                    ps.close()
                } catch (_: SQLException) {}
            }
            if (c != null) {
                try {
                    c.close()
                } catch (_: SQLException) {}
            }
        }

        if (user == null) {
            throw EmptyResultDataAccessException(1)
        }

        return user
    }

    fun deleteAll() {
        jdbcContext.workWithStatementStrategy(object : StatementStrategy {
            override fun makePreparedStatement(c: Connection): PreparedStatement {
                return c.prepareStatement("delete from users")
            }
        })
    }

    fun getCount(): Int {
        var c: Connection? = null
        var ps: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            c = dataSource.connection
            ps = c.prepareStatement("select count(*) from users")
            rs = ps.executeQuery()
            rs.next()
            return rs.getInt(1)
        } catch (e: SQLException) {
            throw e
        } finally {
            if (rs != null) {
                try {
                    rs.close()
                } catch (_: SQLException) {}
            }
            if (ps != null) {
                try {
                    ps.close()
                } catch (_: SQLException) {}
            }
            if (c != null) {
                try {
                    c.close()
                } catch (_: SQLException) {}
            }
        }
    }

}
