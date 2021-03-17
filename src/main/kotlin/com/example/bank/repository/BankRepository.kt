package com.example.bank.repository

import com.example.bank.entity.Bank
import com.example.bank.entity.BankDTO
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BankRepository : JpaRepository<Bank, Int> {
    fun findByOwner(owner: String): Optional<BankDTO>
    fun findByAccountNumber(accountNumber: Int): Optional<BankDTO>
}