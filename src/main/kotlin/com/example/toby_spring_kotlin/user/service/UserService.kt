package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.sql.Connection
import javax.sql.DataSource

class UserService(
    private val userLevelUpgradePolicy: UserLevelUpgradePolicy,
    private val userDao: UserDao,
    private val dataSource: DataSource,
) {

    fun upgradeLevels() {
        TransactionSynchronizationManager.initSynchronization()
        val c: Connection = DataSourceUtils.getConnection(dataSource)
        c.autoCommit = false

        try {
            val users = userDao.getAll()
            users.forEach { user ->
                if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                    userLevelUpgradePolicy.upgradeLevel(user)
                }
            }
            c.commit()
        } catch (e: Exception) {
            c.rollback()
            throw e
        } finally {
            DataSourceUtils.releaseConnection(c, dataSource)
            TransactionSynchronizationManager.unbindResource(dataSource)
            TransactionSynchronizationManager.clearSynchronization()
        }
    }

    fun add(user: User) {
        if (user.level === null) {
            user.level = Level.BASIC
        }
        userDao.add(user)
    }

}