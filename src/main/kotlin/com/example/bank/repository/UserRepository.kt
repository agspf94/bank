package com.example.bank.repository

import com.example.bank.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findByLogin(login: String): User
}