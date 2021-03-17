package com.example.bank.service

import com.example.bank.entity.UserDTO
import com.example.bank.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserService {
    @Autowired
    private lateinit var repository: UserRepository

    fun getUsers(): List<UserDTO> = repository.findAll().stream().map { UserDTO(it) }.collect(Collectors.toList())
}