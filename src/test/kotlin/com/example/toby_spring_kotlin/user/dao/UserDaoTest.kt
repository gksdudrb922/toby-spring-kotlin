package com.example.toby_spring_kotlin.user.dao

import com.example.toby_spring_kotlin.config.CountingDaoFactory
import com.example.toby_spring_kotlin.config.DaoFactory
import com.example.toby_spring_kotlin.infra.CountingDataSource
import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDaoTest {

    @Test
    fun addAndGet() {
        val context = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao = context.getBean("userDao", UserDao::class.java)

        dao.deleteAll()
        assertEquals(0, dao.getCount())

        val user = User().apply {
            id = "1"
            name = "han"
            password = "1234"
        }

        dao.add(user)
        assertEquals(1, dao.getCount())

        val user2 = dao.get(user.id!!)
        assertEquals(user.name, user2.name)
        assertEquals(user.password, user2.password)
    }

    @Test
    fun addAndGetAndCounter() {
        val context = AnnotationConfigApplicationContext(CountingDaoFactory::class.java)
        val dao = context.getBean("userDaoCounting", UserDao::class.java)

        dao.deleteAll()
        assertEquals(0, dao.getCount())

        val user = User().apply {
            id = "2"
            name = "han"
            password = "1234"
        }

        dao.add(user)
        assertEquals(1, dao.getCount())

        val user2 = dao.get(user.id!!)
        assertEquals(user.name, user2.name)
        assertEquals(user.password, user2.password)

        val ccm = context.getBean("dataSourceCounting", CountingDataSource::class.java)
        assertEquals(5, ccm.counter)
    }

}