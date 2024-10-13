package com.example.toby_spring_kotlin.user.domain

enum class Level(
    private val value: Int,
    private val next: Level?
) {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    fun intValue() = value

    fun nextLevel() = next

    companion object {
        fun valueOf(value: Int): Level {
            return entries.firstOrNull { it.value == value }
                ?: throw AssertionError("Unknown value: $value")
        }
    }

}
