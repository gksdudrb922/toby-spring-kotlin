package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.domain.User

interface UserLevelUpgradePolicy {

    fun canUpgradeLevel(user: User): Boolean

    fun upgradeLevel(user: User)

}