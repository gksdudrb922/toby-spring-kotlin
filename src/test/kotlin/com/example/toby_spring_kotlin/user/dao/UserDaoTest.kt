package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.config.CountingDaoFactory
import com.example.toby_spring_kotlin.config.DaoFactory
import com.example.toby_spring_kotlin.infra.CountingDataSource
import com.example.toby_spring_kotlin.user.domain.User
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.dao.EmptyResultDataAccessException
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDaoTest {

    @Test
    fun addAndGet() {
        val context = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao = context.getBean("userDao", UserDao::class.java)

        dao.deleteAll()
        assertEquals(0, dao.getCount())

        val user1 = User(id = "1", name = "han", password = "1234")
        val user2 = User(id = "2", name = "han", password = "1234")

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
    fun addAndGetAndCounter() {
        val context = AnnotationConfigApplicationContext(CountingDaoFactory::class.java)
        val dao = context.getBean("userDaoCounting", UserDao::class.java)

        dao.deleteAll()
        assertEquals(0, dao.getCount())

        val user1 = User(id = "1", name = "han", password = "1234")
        val user2 = User(id = "2", name = "han", password = "1234")

        dao.add(user1)
        dao.add(user2)
        assertEquals(2, dao.getCount())

        val userGet1 = dao.get(user1.id)
        assertEquals(user1.name, userGet1.name)
        assertEquals(user1.password, userGet1.password)

        val userGet2 = dao.get(user2.id)
        assertEquals(user2.name, userGet2.name)
        assertEquals(user2.password, userGet2.password)

        val ccm = context.getBean("dataSourceCounting", CountingDataSource::class.java)
        assertEquals(7, ccm.counter)
    }

    @Test
    fun count() {
        val context = AnnotationConfigApplicationContext(CountingDaoFactory::class.java)
        val dao = context.getBean("userDaoCounting", UserDao::class.java)

        dao.deleteAll()
        assertEquals(0, dao.getCount())

        val user1 = User(id = "1", name = "han", password = "1234")
        val user2 = User(id = "2", name = "han", password = "1234")
        val user3 = User(id = "3", name = "han", password = "1234")

        dao.add(user1)
        assertEquals(1, dao.getCount())

        dao.add(user2)
        assertEquals(2, dao.getCount())

        dao.add(user3)
        assertEquals(3, dao.getCount())
    }

    @Test
    fun getUserFailure() {
        val context = AnnotationConfigApplicationContext(CountingDaoFactory::class.java)
        val dao = context.getBean("userDaoCounting", UserDao::class.java)

        dao.deleteAll()
        assertEquals(0, dao.getCount())

        assertThrows<EmptyResultDataAccessException> { dao.get("unknown_id") }
    }

}