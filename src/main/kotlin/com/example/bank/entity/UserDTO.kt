package com.example.bank.entity

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class UserDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    var login: String,
    var pass: String,
    var name: String,
    var roles: MutableCollection<Role>
    //var email: String,
) {
    constructor(user: User) : this(
        user.id,
        user.login,
        user.pass,
        user.name,
        //user.email,
        user.roles,
    )
}