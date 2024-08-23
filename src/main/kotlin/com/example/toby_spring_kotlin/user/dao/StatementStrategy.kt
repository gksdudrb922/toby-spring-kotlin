package com.example.toby_spring_kotlin.user.dao

import java.sql.Connection
import java.sql.PreparedStatement

interface StatementStrategy {

    fun makePreparedStatement(c: Connection): PreparedStatement

}