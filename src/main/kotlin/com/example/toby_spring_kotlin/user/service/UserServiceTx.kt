package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

class UserServiceTx(
    private val userService: UserService,
    private val transactionManager: PlatformTransactionManager,
) : UserService {

    override fun add(user: User) {
        userService.add(user)
    }

    override fun upgradeLevels() {
        val status: TransactionStatus = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            userService.upgradeLevels()
            transactionManager.commit(status)
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }

}