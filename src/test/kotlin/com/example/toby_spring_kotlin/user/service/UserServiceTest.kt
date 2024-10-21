package com.example.toby_spring_kotlin.user.service

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.Level
import com.example.toby_spring_kotlin.user.domain.User
import com.example.toby_spring_kotlin.user.service.DefaultUserLevelUpgradePolicy.Companion.MIN_LOGCOUNT_FOR_SILVER
import com.example.toby_spring_kotlin.user.service.DefaultUserLevelUpgradePolicy.Companion.MIN_RECCOUNT_FOR_GOLD
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.transaction.PlatformTransactionManager
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

    @Autowired
    @Qualifier("testMailSender")
    private lateinit var mailSender: MailSender

    @Autowired
    @Qualifier("testTransactionManager")
    private lateinit var transactionManager: PlatformTransactionManager

    private lateinit var users: List<User>

    @BeforeTest
    fun setUp() {
        userDao.deleteAll()
        users = listOf(
            User(id = "1", name = "han", password = "1234", level = Level.BASIC, login = MIN_LOGCOUNT_FOR_SILVER - 1, recommend = 0, email = "123"),
            User(id = "2", name = "han", password = "1234", level = Level.BASIC, login = MIN_LOGCOUNT_FOR_SILVER, recommend = 0, email = "123"),
            User(id = "3", name = "han", password = "1234", level = Level.SILVER, login = 60, recommend = MIN_RECCOUNT_FOR_GOLD - 1, email = "123"),
            User(id = "4", name = "han", password = "1234", level = Level.SILVER, login = 60, recommend = MIN_RECCOUNT_FOR_GOLD, email = "123"),
            User(id = "5", name = "han", password = "1234", level = Level.GOLD, login = 100, recommend = Int.MAX_VALUE, email = "123"),
        )
    }

    @Test
    fun upgradeLevels() {
        val mockUserDao = MockUserDao(users)
        val mockMailSender = MockMailSender()

        val userServiceImpl =
            UserServiceImpl(DefaultUserLevelUpgradePolicy(mockUserDao, mockMailSender), mockUserDao)

        userServiceImpl.upgradeLevels()

        val updated = mockUserDao.updated
        assertEquals(2, updated.size)
        checkUserAndLevel(updated[0], "2", Level.SILVER)
        checkUserAndLevel(updated[1], "4", Level.GOLD)

        val requests = mockMailSender.requests
        assertEquals(2, requests.size)
        assertEquals(users[1].email, requests[0])
        assertEquals(users[3].email, requests[1])
    }

    private fun checkUserAndLevel(updated: User, expectedId: String, expectedLevel: Level) {
        assertEquals(expectedId, updated.id)
        assertEquals(expectedLevel, updated.level)
    }

    @Test
    fun mockUpgradeLevels() {
        val mockUserDao = mockk<UserDao>(relaxed = true)
        val mockMailSender = mockk<MailSender>(relaxed = true)
        every { mockUserDao.getAll() } returns users

        val userServiceImpl =
            UserServiceImpl(DefaultUserLevelUpgradePolicy(mockUserDao, mockMailSender), mockUserDao)

        userServiceImpl.upgradeLevels()

        verify(exactly = 2) { mockUserDao.update(any(User::class)) }
        verify { mockUserDao.update(users[1]) }
        assertEquals(Level.SILVER, users[1].level)
        verify { mockUserDao.update(users[3]) }
        assertEquals(Level.GOLD, users[3].level)

        val mailMessageSlot = mutableListOf<SimpleMailMessage>()
        verify(exactly = 2) { mockMailSender.send(capture(mailMessageSlot)) }
        assertEquals(users[1].email, mailMessageSlot[0].to!![0])
        assertEquals(users[3].email, mailMessageSlot[1].to!![0])
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

        val txUserService = UserServiceTx(
            UserServiceImpl(TestUserLevelUpgradePolicy(userDao, mailSender, users[3].id), userDao),
            transactionManager
        )

        try {
            txUserService.upgradeLevels()
            fail("TestUserServiceException expected")
        } catch (_: TestUserServiceException) {
            checkLevelUpgraded(users[1], false)
        }
    }

    private fun checkLevelUpgraded(user: User, upgraded: Boolean) {
        val userUpdate = userDao.get(user.id)
        when (upgraded) {
            true -> assertEquals(user.level?.nextLevel(), userUpdate.level)
            false -> assertEquals(user.level, userUpdate.level)
        }
    }

}

class TestUserLevelUpgradePolicy (
    userDao: UserDao,
    mailSender: MailSender,
    private val id: String,
) : DefaultUserLevelUpgradePolicy(userDao, mailSender) {

    override fun upgradeLevel(user: User) {
        if (user.id == id) {
            throw TestUserServiceException()
        }
        super.upgradeLevel(user)
    }

}

class TestUserServiceException : RuntimeException()

class MockMailSender: MailSender {

    val requests: MutableList<String> = mutableListOf()

    override fun send(simpleMessage: SimpleMailMessage) {
        requests.add(simpleMessage.to!![0])
    }

    override fun send(vararg simpleMessages: SimpleMailMessage?) {

    }

}

class MockUserDao(
    private val users: List<User>
) : UserDao {

    val updated: MutableList<User> = mutableListOf()

    override fun add(user: User) {
        throw UnsupportedOperationException()
    }

    override fun get(id: String): User {
        throw UnsupportedOperationException()
    }

    override fun deleteAll() {
        throw UnsupportedOperationException()
    }

    override fun getCount(): Int {
        throw UnsupportedOperationException()
    }

    override fun getAll(): List<User> = users

    override fun update(user: User) {
        updated.add(user)
    }

}