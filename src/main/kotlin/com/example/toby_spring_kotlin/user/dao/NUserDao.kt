package com.example.toby_spring_kotlin.user.dao

import java.sql.Connection
import java.sql.DriverManager

class NUserDao: UserDao() {

    override fun getConnection(): Connection =
        DriverManager.getConnection("jdbc:h2:~/test", "sa", "")

}