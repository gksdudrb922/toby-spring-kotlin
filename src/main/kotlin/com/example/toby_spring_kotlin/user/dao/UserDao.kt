package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.user.domain.User

interface UserDao {

    fun add(user: User)

    fun get(id: String): User

    fun deleteAll()

    fun getCount(): Int

    fun getAll(): List<User>

}