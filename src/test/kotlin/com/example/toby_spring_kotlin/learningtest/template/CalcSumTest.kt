package com.example.toby_spring_kotlin.learningtest.template

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CalcSumTest {

    private lateinit var calculator: Calculator
    private lateinit var numFilePath: String

    @BeforeTest
    fun setUp() {
        calculator = Calculator()
        numFilePath = javaClass.getResource("/learningtest/numbers.txt")!!.path
    }

    @Test
    fun sumOfNumbers() {
        assertEquals(10, calculator.calcSum(numFilePath))
    }

    @Test
    fun multiplyOfNumbers() {
        assertEquals(24, calculator.multiplyOfNumbers(numFilePath))
    }

    @Test
    fun concatenateStrings() {
        assertEquals("1234", calculator.concatenate(numFilePath))
    }

}