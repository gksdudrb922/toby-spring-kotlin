package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

open class DefaultUserLevelUpgradePolicy(
    private val userDao: UserDao,
    private val mailSender: MailSender,
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
        sendUpgradeEMail(user)
    }

    private fun sendUpgradeEMail(user: User) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(user.email)
        mailMessage.from = "useradmin@ksug.org"
        mailMessage.subject = "Upgrade 안내"
        mailMessage.text = "사용자님의 등급이 ${user.level!!.name}로 업그레이드되었습니다"

        mailSender.send(mailMessage)
    }

}