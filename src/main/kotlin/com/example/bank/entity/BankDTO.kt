package com.example.bank.entity

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class BankDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "account_number")
    var accountNumber: Int,

    var owner: String,
    var balance: Double,

//    var document: String,
) {
    constructor(bank: Bank) : this(
        bank.id,
        bank.accountNumber,
        bank.owner,
        bank.balance)

//    constructor(bank: Bank) : this(bank.id, bank.accountNumber, bank.owner, bank.balance, bank.document)

//    companion object {
//        fun create(bank: Bank): BankDTO = BankDTO(bank)
//    }
}