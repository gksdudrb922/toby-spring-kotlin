package com.example.toby_spring_kotlin.user.dao

import java.sql.Connection
import java.sql.DriverManager

class DConnectionMaker: ConnectionMaker {

    override fun makeConnection(): Connection =
        DriverManager.getConnection("jdbc:h2:~/test", "sa", "")

}