package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User
import jakarta.mail.Message
import jakarta.mail.MessagingException
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.AddressException
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.io.UnsupportedEncodingException
import java.util.Properties

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
        sendUpgradeEMail(user)
    }

    private fun sendUpgradeEMail(user: User) {
        val props = Properties()
        props["mail.smtp.host"] = "mail.ksug.org"
        val s = Session.getInstance(props, null)

        val message = MimeMessage(s)
        try {
            message.setFrom(InternetAddress("useradmin@ksug.org"))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(user.email))
            message.subject = "Upgrade 안내"
            message.setText("사용자님의 등급이 ${user.level!!.name}로 업그레이드되었습니다")

            Transport.send(message)
        } catch (e: Exception) {
            when (e) {
                is AddressException, is MessagingException, is UnsupportedEncodingException -> throw RuntimeException(e)
            }
        }


    }

}