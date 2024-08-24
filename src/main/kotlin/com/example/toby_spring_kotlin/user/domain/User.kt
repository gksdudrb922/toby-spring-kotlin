package com.example.toby_spring_kotlin.user.domain

class User(
    val id: String,
    var name: String,
    var password: String,
    var level: Level,
    var login: Int,
    var recommend: Int,
)
