package com.example.toby_spring_kotlin.user.domain

enum class Level(private val value: Int) {
    BASIC(1), SILVER(2), GOLD(3);

    fun intValue() = value

    companion object {
        fun valueOf(value: Int): Level {
            return entries.firstOrNull { it.value == value }
                ?: throw AssertionError("Unknown value: $value")
        }
    }

}
