package com.example.bank.entity

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    var name: String,
) : GrantedAuthority {
    override fun getAuthority(): String = name
}