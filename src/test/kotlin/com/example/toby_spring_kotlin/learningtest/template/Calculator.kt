package com.example.toby_spring_kotlin.learningtest.template

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class Calculator {

    fun calcSum(filepath: String): Int {
        val sumCallback = object : LineCallback<Int> {
            override fun doSomethingWithLine(line: String, value: Int): Int {
                return value + line.toInt()
            }
        }
        return lineReadTemplate(filepath, sumCallback, 0)
    }

    fun multiplyOfNumbers(numFilePath: String): Int {
        val multiplyCallback = object : LineCallback<Int> {
            override fun doSomethingWithLine(line: String, value: Int): Int {
                return value * line.toInt()
            }
        }
        return lineReadTemplate(numFilePath, multiplyCallback, 1)
    }

    fun concatenate(filepath: String): String {
        val concatenateCallback = object : LineCallback<String> {
            override fun doSomethingWithLine(line: String, value: String): String {
                return value + line
            }
        }
        return lineReadTemplate(filepath, concatenateCallback, "")
    }

    private fun <T> lineReadTemplate(filepath: String, callback: LineCallback<T>, initVal: T): T {
        var br: BufferedReader? = null

        try {
            br = BufferedReader(FileReader(filepath))
            var res = initVal
            var line: String?
            while ((br.readLine().also { line = it }) != null) {
                res = callback.doSomethingWithLine(line!!, res)
            }
            return res
        } catch (e: IOException) {
            println(e.message)
            throw e
        } finally {
            if (br != null) {
                try {
                    br.close()
                } catch (e: IOException) {
                    println(e.message)
                }
            }
        }
    }

}