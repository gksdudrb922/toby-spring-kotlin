package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

class UserService(
    private val userLevelUpgradePolicy: UserLevelUpgradePolicy,
    private val userDao: UserDao,
    private val transactionManager: PlatformTransactionManager,
) {

    fun upgradeLevels() {
        val status: TransactionStatus = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            upgradeLevelsInternal()
            transactionManager.commit(status)
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }

    private fun upgradeLevelsInternal() {
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