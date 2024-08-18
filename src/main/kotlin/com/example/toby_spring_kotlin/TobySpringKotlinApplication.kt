package com.example.toby_spring_kotlin

import com.example.toby_spring_kotlin.config.CountingDaoFactory
import com.example.toby_spring_kotlin.config.DaoFactory
import com.example.toby_spring_kotlin.infra.CountingDataSource
import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.AnnotationConfigApplicationContext

@SpringBootApplication
class TobySpringKotlinApplication

fun main(args: Array<String>) {
	runApplication<TobySpringKotlinApplication>(*args)

	userDaoTest()
	userDaoCountingTest()
}

private fun userDaoTest() {
	val context = AnnotationConfigApplicationContext(DaoFactory::class.java)
	val dao = context.getBean("userDao", UserDao::class.java)

	val user = User().apply {
		id = "1"
		name = "han"
		password = "1234"
	}

	dao.add(user)

	println("${user.id} 등록 성공")

	val user2 = dao.get(user.id!!)
	if (user.name != user2.name) {
		println("테스트 실패 (name)")
	} else if (user.password != user2.password) {
		println("테스트 실패 (password)")
	} else {
		println("조회 테스트 성공")
	}
}

private fun userDaoCountingTest() {
	val context = AnnotationConfigApplicationContext(CountingDaoFactory::class.java)
	val dao = context.getBean("userDaoCounting", UserDao::class.java)

	val user = User().apply {
		id = "2"
		name = "han"
		password = "1234"
	}

	dao.add(user)

	println("${user.id} 등록 성공")

	val user2 = dao.get(user.id!!)
	if (user.name != user2.name) {
		println("테스트 실패 (name)")
	} else if (user.password != user2.password) {
		println("테스트 실패 (password)")
	} else {
		println("조회 테스트 성공")
	}

	val ccm = context.getBean("dataSourceCounting", CountingDataSource::class.java)
	if (ccm.counter != 2) {
		println("테스트 실패 (counter)")
	} else {
		println("카운터 테스트 성공")
	}
}
