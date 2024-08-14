package com.example.toby_spring_kotlin.user.dao

import java.sql.Connection
import java.sql.DriverManager

class DUserDao: UserDao() {

    override fun getConnection(): Connection =
        DriverManager.getConnection("jdbc:h2:~/test", "sa", "")

}