package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.infra.CountingDataSource
import com.example.toby_spring_kotlin.user.domain.User
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
import java.sql.SQLException
import javax.sql.DataSource
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
class UserDaoTest {

    @Autowired
    @Qualifier("testUserDao")
    private lateinit var dao: UserDao

    @Autowired
    @Qualifier("testDataSource")
    private lateinit var dataSource: DataSource

    @Autowired
    @Qualifier("testUserDaoCounting")
    private lateinit var daoCounting: UserDao

    @Autowired
    @Qualifier("testDataSourceCounting")
    private lateinit var dataSourceCounting: CountingDataSource

    private val user1 = User(id = "1", name = "han", password = "1234")
    private val user2 = User(id = "2", name = "han", password = "1234")
    private val user3 = User(id = "3", name = "han", password = "1234")

    @BeforeTest
    fun setup() {
        dao.deleteAll()
        assertEquals(0, dao.getCount())
    }

    @Test
    fun addAndGet() {
        dao.add(user1)
        dao.add(user2)
        assertEquals(2, dao.getCount())

        val userGet1 = dao.get(user1.id)
        assertEquals(user1.name, userGet1.name)
        assertEquals(user1.password, userGet1.password)

        val userGet2 = dao.get(user2.id)
        assertEquals(user2.name, userGet2.name)
        assertEquals(user2.password, userGet2.password)
    }

    @Test
    fun dataSourceCounting() {
        daoCounting.add(user1)
        daoCounting.get(user1.id)

        assertEquals(2, dataSourceCounting.counter)
    }

    @Test
    fun count() {
        dao.add(user1)
        assertEquals(1, dao.getCount())

        dao.add(user2)
        assertEquals(2, dao.getCount())

        dao.add(user3)
        assertEquals(3, dao.getCount())
    }

    @Test
    fun getUserFailure() {
        assertThrows<EmptyResultDataAccessException> { dao.get("unknown_id") }
    }

    @Test
    fun getAll() {
        val users0 = dao.getAll()
        assertEquals(0, users0.size)

        dao.add(user1)
        val users1 = dao.getAll()
        assertEquals(1, users1.size)
        checkSameUser(user1, users1[0])

        dao.add(user2)
        val users2 = dao.getAll()
        assertEquals(2, users2.size)
        checkSameUser(user1, users2[0])
        checkSameUser(user2, users2[1])

        dao.add(user3)
        val users3 = dao.getAll()
        assertEquals(3, users3.size)
        checkSameUser(user1, users3[0])
        checkSameUser(user2, users3[1])
        checkSameUser(user3, users3[2])
    }

    private fun checkSameUser(user1: User, user2: User) {
        assertEquals(user1.id, user2.id)
        assertEquals(user1.name, user2.name)
        assertEquals(user1.password, user2.password)
    }

    @Test
    fun duplicateKey() {
        dao.add(user1)
        assertThrows<DuplicateKeyException> { dao.add(user1) }
    }

    @Test
    fun sqlExceptionTranslate() {
        try {
            dao.add(user1)
            dao.add(user1)
        } catch (e: DuplicateKeyException) {
            val sqlEx = e.rootCause as SQLException
            val set = SQLErrorCodeSQLExceptionTranslator(dataSource)
            assertEquals(
                DuplicateKeyException::class.java,
                set.translate(null.toString(), null, sqlEx)!!::class.java
            )
        }
    }

}