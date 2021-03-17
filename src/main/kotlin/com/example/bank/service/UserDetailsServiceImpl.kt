package com.example.bank.service

import com.example.bank.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service(value = "userDetailsService")
class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    private lateinit var repository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails = repository.findByLogin(username)

        // Security - Mode 2
//        return when (username) {
//            "user" -> User.withUsername(username).password(encoder.encode("user")).roles("USER").build()
//            "admin" -> User.withUsername(username).password(encoder.encode("admin")).roles("ADMIN", "USER").build()
//            else -> throw UsernameNotFoundException("The given username was not found!")
//        }
//    }
}