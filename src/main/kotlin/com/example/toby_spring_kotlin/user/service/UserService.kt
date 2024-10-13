package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User

class UserService(
    private val userLevelUpgradePolicy: UserLevelUpgradePolicy,
    private val userDao: UserDao,
) {

    fun upgradeLevels() {
        val users = userDao.getAll()
        users.forEach { user ->
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user)
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