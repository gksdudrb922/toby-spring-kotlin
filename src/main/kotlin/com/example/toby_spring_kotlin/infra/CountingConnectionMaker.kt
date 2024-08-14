package com.example.toby_spring_kotlin.infra

import java.sql.Connection

class CountingConnectionMaker(
    private val realConnectionMaker: ConnectionMaker,
): ConnectionMaker {

    var counter = 0
        private set

    override fun makeConnection(): Connection {
        counter++
        return realConnectionMaker.makeConnection()
    }

}