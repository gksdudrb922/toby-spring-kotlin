package com.example.toby_spring_kotlin.user.dao

import java.sql.Connection

interface ConnectionMaker {

    fun makeConnection(): Connection

}