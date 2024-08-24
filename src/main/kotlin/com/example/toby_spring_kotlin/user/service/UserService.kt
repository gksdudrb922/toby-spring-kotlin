package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User

class UserService(
    private val userDao: UserDao,
) {

    fun upgradeLevels() {
        val users = userDao.getAll()
        users.forEach { user ->
            val changed: Boolean
            if (user.level === Level.BASIC && user.login >= 50) {
                user.level = Level.SILVER
                changed = true
            } else if (user.level === Level.SILVER && user.recommend >= 30) {
                user.level = Level.GOLD
                changed = true
            } else if (user.level === Level.GOLD) {
                changed = false
            } else {
                changed = false
            }
            if (changed) {
                userDao.update(user)
            }
        }
    }

    fun add(user: User) {
        if (user.level === null) {
            user.level = Level.BASIC
        }
        userDao.add(user)
    }

}