package com.example.toby_spring_kotlin.user.domain

import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserTest {

    private lateinit var user: User

    @BeforeTest
    fun setUp() {
        user = User(id = "1", name = "han", password = "1234", level = null, login = 1, recommend = 0)
    }

    @Test
    fun upgradeLevel() {
        Level.entries.forEach { level ->
            if (level.nextLevel() == null) {
                return
            }
            user.level = level
            user.upgradeLevel()
            assertEquals(level.nextLevel(), user.level)
        }
    }

    @Test
    fun cannotUpgradeLevel() {
        Level.entries.forEach { level ->
            if (level.nextLevel() != null) {
                return
            }
            user.level = level
            assertThrows<IllegalStateException> { user.upgradeLevel() }
        }
    }

}