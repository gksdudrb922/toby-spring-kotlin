package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.domain.User

interface UserService {

    fun add(user: User)

    fun upgradeLevels()

}