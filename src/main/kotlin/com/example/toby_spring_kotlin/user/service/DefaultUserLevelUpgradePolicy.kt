package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User

open class DefaultUserLevelUpgradePolicy(
    private val userDao: UserDao,
) : UserLevelUpgradePolicy {

    companion object {
        const val MIN_LOGCOUNT_FOR_SILVER = 50
        const val MIN_RECCOUNT_FOR_GOLD = 30
    }

    override fun canUpgradeLevel(user: User): Boolean =
        when (user.level) {
            Level.BASIC -> user.login >= MIN_LOGCOUNT_FOR_SILVER
            Level.SILVER -> user.recommend >= MIN_RECCOUNT_FOR_GOLD
            Level.GOLD -> false
            else -> throw IllegalStateException("Unknown level: ${user.level}")
        }

    override fun upgradeLevel(user: User) {
        user.upgradeLevel()
        userDao.update(user)
    }

}