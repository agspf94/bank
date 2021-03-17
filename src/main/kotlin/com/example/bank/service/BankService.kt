package com.example.bank.service

import com.example.bank.exception.exceptions.OwnerNotFoundException
import com.example.bank.entity.Bank
import com.example.bank.entity.BankDTO
import com.example.bank.exception.exceptions.AccountNumberAlreadyExistsException
import com.example.bank.exception.exceptions.AccountNumberNotFoundException
import com.example.bank.repository.BankRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class BankService(
    @Autowired
    private var repository: BankRepository
) {
    fun getBanks(pageable: Pageable): List<BankDTO> = repository.findAll(pageable).stream().map { BankDTO(it) }.collect(Collectors.toList())

    fun getBankByAccountNumber(accountNumber: Int): BankDTO =
        repository
            .findByAccountNumber(accountNumber)
            .map { it }
            .orElseThrow { AccountNumberNotFoundException("The given account number $accountNumber was not found!") }
//        val bank: Optional<BankDTO> = repository.findByAccountNumber(accountNumber)
//        if (bank.isPresent) {
//            return bank
//        } else {
//            throw AccountNumberNotFoundException("The given account number $accountNumber was not found!")
//        }
//    }

    fun getBankByOwner(owner: String): BankDTO =
        repository
            .findByOwner(owner)
            .map { it }
            .orElseThrow { OwnerNotFoundException("The given owner $owner was not found!") }
//        val bank: Optional<BankDTO> = repository.findByOwner(owner)
//        if (bank.isPresent) {
//            return bank
//        } else {
//            throw OwnerNotFoundException("The given owner $owner was not found!")
//        }
//    }

    fun addBank(bank: Bank): BankDTO {
        val optional: Optional<BankDTO> = repository.findByAccountNumber(bank.accountNumber)
        return if (!optional.isPresent) {
            BankDTO(repository.save(bank))
        } else {
            throw AccountNumberAlreadyExistsException("The given account number ${bank.accountNumber} already exists!")
        }
    }

    fun updateBank(accountNumber: Int, bank: Bank): BankDTO {
        val bankDTO = repository.findByAccountNumber(accountNumber)
        if (bankDTO.isPresent) {
            val updated: Bank = repository.findById(bankDTO.get().id).get()

            updated.accountNumber = bank.accountNumber
            updated.owner = bank.owner
            updated.balance = bank.balance
            updated.document = bank.document

            return BankDTO(repository.save(updated))
        } else {
            throw AccountNumberNotFoundException("The given account number $accountNumber was not found!")
        }
    }

    fun deleteBank(accountNumber: Int) {
        val bankDTO = repository.findByAccountNumber(accountNumber)
        bankDTO
            .map { repository.deleteById(bankDTO.get().id) }
            .orElseThrow{ AccountNumberNotFoundException("The given account number $accountNumber was not found!") }
//        if (bankDTO.isPresent) {
//            repository.deleteById(bankDTO.get().id)
//        } else {
//            throw AccountNumberNotFoundException("The given account number $accountNumber was not found!")
//        }
    }
}