package com.example.bank.entity

import javax.persistence.*

@Entity(name = "bank")
data class Bank(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "account_number")
    var accountNumber: Int,

    var owner: String,
    var balance: Double,
    var document: String,
)