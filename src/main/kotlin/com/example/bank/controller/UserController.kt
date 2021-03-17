package com.example.bank.controller

import com.example.bank.entity.User
import com.example.bank.entity.UserDTO
import com.example.bank.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired
    private lateinit var service: UserService

    @GetMapping
    @Secured("ROLE_ADMIN")
    fun getUsers(): ResponseEntity<List<UserDTO>> = ResponseEntity.ok(service.getUsers())

    @GetMapping("/info")
    fun getUsersInfo(@AuthenticationPrincipal user: User): UserDTO = UserDTO(user)
}