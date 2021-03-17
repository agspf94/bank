package com.example.bank.controller

import com.example.bank.entity.Index
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class IndexController {
    @GetMapping
    fun getIndex(): ResponseEntity<Index> = ResponseEntity.ok(Index("Welcome to our Bank!"))
}