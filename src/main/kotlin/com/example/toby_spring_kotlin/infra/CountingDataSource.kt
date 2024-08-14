package com.example.toby_spring_kotlin.infra

import java.io.PrintWriter
import java.sql.Connection
import java.util.logging.Logger
import javax.sql.DataSource

class CountingDataSource(
    private val realDataSource: DataSource,
): DataSource {

    var counter = 0
        private set

    override fun getLogWriter(): PrintWriter {
        return realDataSource.logWriter
    }

    override fun setLogWriter(out: PrintWriter?) {
        return realDataSource.setLogWriter(out)
    }

    override fun setLoginTimeout(seconds: Int) {
        return realDataSource.setLoginTimeout(seconds)
    }

    override fun getLoginTimeout(): Int {
        return realDataSource.loginTimeout
    }

    override fun getParentLogger(): Logger {
        return realDataSource.parentLogger
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        return realDataSource.unwrap(iface)
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        return realDataSource.isWrapperFor(iface)
    }

    override fun getConnection(): Connection {
        counter++
        return realDataSource.connection
    }

    override fun getConnection(username: String?, password: String?): Connection {
        counter++
        return realDataSource.getConnection(username, password)
    }

}