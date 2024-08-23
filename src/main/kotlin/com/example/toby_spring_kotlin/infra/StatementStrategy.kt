package com.example.toby_spring_kotlin.infra

import java.sql.Connection
import java.sql.PreparedStatement

interface StatementStrategy {

    fun makePreparedStatement(c: Connection): PreparedStatement

}