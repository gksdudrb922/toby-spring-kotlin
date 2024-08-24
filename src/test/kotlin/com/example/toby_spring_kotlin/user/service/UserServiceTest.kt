package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@SpringBootTest
class UserServiceTest {

    @Autowired
    @Qualifier("testUserService")
    private lateinit var userService: UserService

    @Autowired
    @Qualifier("testUserDao")
    private lateinit var userDao: UserDao

    private lateinit var users: List<User>

    @BeforeTest
    fun setUp() {
        userDao.deleteAll()
        users = listOf(
            User(id = "1", name = "han", password = "1234", level = Level.BASIC, login = 49, recommend = 0),
            User(id = "2", name = "han", password = "1234", level = Level.BASIC, login = 50, recommend = 0),
            User(id = "3", name = "han", password = "1234", level = Level.SILVER, login = 60, recommend = 29),
            User(id = "4", name = "han", password = "1234", level = Level.SILVER, login = 60, recommend = 30),
            User(id = "5", name = "han", password = "1234", level = Level.GOLD, login = 100, recommend = 100),
        )
    }

    @Test
    fun upgradeLevels() {
        users.forEach { user -> userDao.add(user) }

        userService.upgradeLevels()
        checkLevel(Level.BASIC, users[0])
        checkLevel(Level.SILVER, users[1])
        checkLevel(Level.SILVER, users[2])
        checkLevel(Level.GOLD, users[3])
        checkLevel(Level.GOLD, users[4])

    }

    private fun checkLevel(expectedLevel: Level, user: User) {
        val userUpdate = userDao.get(user.id)
        assertEquals(expectedLevel, userUpdate.level)
    }

    @Test
    fun add() {
        val userWithLevel = users[4]
        val userWithoutLevel = users[0]
        userWithoutLevel.level = null

        userService.add(userWithLevel)
        userService.add(userWithoutLevel)

        val userWithLevelRead = userDao.get(userWithLevel.id)
        val userWithoutLevelRead = userDao.get(userWithoutLevel.id)

        assertEquals(userWithLevel.level, userWithLevelRead.level)
        assertEquals(Level.BASIC, userWithoutLevelRead.level)
    }

}