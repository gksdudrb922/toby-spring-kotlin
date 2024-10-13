package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User
import com.example.toby_spring_kotlin.user.service.DefaultUserLevelUpgradePolicy.Companion.MIN_LOGCOUNT_FOR_SILVER
import com.example.toby_spring_kotlin.user.service.DefaultUserLevelUpgradePolicy.Companion.MIN_RECCOUNT_FOR_GOLD
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.fail

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
            User(id = "1", name = "han", password = "1234", level = Level.BASIC, login = MIN_LOGCOUNT_FOR_SILVER - 1, recommend = 0),
            User(id = "2", name = "han", password = "1234", level = Level.BASIC, login = MIN_LOGCOUNT_FOR_SILVER, recommend = 0),
            User(id = "3", name = "han", password = "1234", level = Level.SILVER, login = 60, recommend = MIN_RECCOUNT_FOR_GOLD - 1),
            User(id = "4", name = "han", password = "1234", level = Level.SILVER, login = 60, recommend = MIN_RECCOUNT_FOR_GOLD),
            User(id = "5", name = "han", password = "1234", level = Level.GOLD, login = 100, recommend = Int.MAX_VALUE),
        )
    }

    @Test
    fun upgradeLevels() {
        users.forEach { user -> userDao.add(user) }

        userService.upgradeLevels()
        checkLevelUpgraded(users[0], false)
        checkLevelUpgraded(users[1], true)
        checkLevelUpgraded(users[2], false)
        checkLevelUpgraded(users[3], true)
        checkLevelUpgraded(users[4], false)

    }

    private fun checkLevelUpgraded(user: User, upgraded: Boolean) {
        val userUpdate = userDao.get(user.id)
        when (upgraded) {
            true -> assertEquals(user.level?.nextLevel(), userUpdate.level)
            false -> assertEquals(user.level, userUpdate.level)
        }
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

    @Test
    fun upgradeAllOrNothing() {
        users.forEach { user -> userDao.add(user) }
        val testUserService = UserService(TestUserLevelUpgradePolicy(userDao, users[3].id), userDao)

        try {
            testUserService.upgradeLevels()
            fail("TestUserServiceException expected")
        } catch (_: TestUserServiceException) {
            checkLevelUpgraded(users[1], true)
        }
    }

}

class TestUserLevelUpgradePolicy (
    userDao: UserDao,
    private val id: String,
) : DefaultUserLevelUpgradePolicy(userDao) {

    override fun upgradeLevel(user: User) {
        if (user.id == id) {
            throw TestUserServiceException()
        }
        super.upgradeLevel(user)
    }

}

class TestUserServiceException : RuntimeException()