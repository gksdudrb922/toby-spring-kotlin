package com.example.toby_spring_kotlin.user.dao

class DaoFactory {

    fun userDao(): UserDao {
        val connectionMaker = DConnectionMaker()
        return UserDao(connectionMaker)
    }

}