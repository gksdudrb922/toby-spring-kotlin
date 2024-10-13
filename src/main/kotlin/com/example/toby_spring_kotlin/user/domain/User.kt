package com.example.toby_spring_kotlin.user.domain

class User(
    val id: String,
    var name: String,
    var password: String,
    var level: Level? = null,
    var login: Int,
    var recommend: Int,
    var email: String,
 ) {

    fun upgradeLevel() {
        val nextLevel = level?.nextLevel()
        checkNotNull(nextLevel) { "${level}은 업그레이드가 불가능합니다" }
        level = nextLevel
    }

}

