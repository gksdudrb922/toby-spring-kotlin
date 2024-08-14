package com.example.toby_spring_kotlin.infra

import java.sql.Connection

interface ConnectionMaker {

    fun makeConnection(): Connection

}