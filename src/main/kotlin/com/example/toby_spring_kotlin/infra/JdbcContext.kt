package com.example.toby_spring_kotlin.infra

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

class JdbcContext(
    private val dataSource: DataSource
) {

    fun workWithStatementStrategy(stmt: StatementStrategy) {
        var c: Connection? = null
        var ps: PreparedStatement? = null

        try {
            c = dataSource.connection
            ps = stmt.makePreparedStatement(c)
            ps.executeUpdate()
        } catch (e: SQLException) {
            throw e
        } finally {
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