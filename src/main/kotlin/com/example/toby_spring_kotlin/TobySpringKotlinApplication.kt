package com.example.toby_spring_kotlin

import com.example.toby_spring_kotlin.user.dao.UserDao
import com.example.toby_spring_kotlin.user.domain.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TobySpringKotlinApplication

fun main(args: Array<String>) {
	runApplication<TobySpringKotlinApplication>(*args)

	val dao = UserDao()

	val user = User().apply {
		id = "1"
		name = "han"
		password = "1234"
	}

	dao.add(user)

	println("${user.id} 등록 성공")

	val user2 = dao.get(user.id!!)
	println(user2.name)
	println(user2.password)

	println("${user2.id} 조회 성공")
}
