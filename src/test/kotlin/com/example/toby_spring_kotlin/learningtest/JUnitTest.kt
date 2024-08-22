package com.example.toby_spring_kotlin.learningtest

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.assertTrue

@SpringBootTest
class JUnitTest {

    @Autowired
    private lateinit var context: ApplicationContext

    companion object {
        val testObjects = HashSet<JUnitTest>()
        var contextObject: ApplicationContext? = null
    }

    @Test
    fun test1() {
        assertFalse { testObjects.contains(this) }
        testObjects.add(this)

        assertTrue { contextObject === null || contextObject === this.context }
        contextObject = this.context
    }

    @Test
    fun test2() {
        assertFalse { testObjects.contains(this) }
        testObjects.add(this)

        assertTrue { contextObject === null || contextObject === this.context }
        contextObject = this.context
    }

    @Test
    fun test3() {
        assertFalse { testObjects.contains(this) }
        testObjects.add(this)

        assertTrue { contextObject === null || contextObject === this.context }
        contextObject = this.context
    }

}