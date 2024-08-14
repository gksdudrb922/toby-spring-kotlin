package com.example.toby_spring_kotlin.infra

import java.sql.Connection
import java.sql.DriverManager

class NConnectionMaker {
    fun makeConnection(): Connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "")
}