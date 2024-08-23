package com.example.toby_spring_kotlin.learningtest.template

interface LineCallback<T> {

    fun doSomethingWithLine(line: String, value: T): T

}