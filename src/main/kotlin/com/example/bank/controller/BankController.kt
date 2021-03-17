package com.example.bank.controller

import com.example.bank.entity.BankDTO
import com.example.bank.service.BankService
import com.example.bank.entity.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/banks")
class BankController(
    @Autowired
    private var service: BankService
) {
    private fun getURI(id: Int): URI =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()

    @GetMapping
    fun getBanks(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int
    ): ResponseEntity<List<BankDTO>> =
        ResponseEntity.ok(service.getBanks(PageRequest.of(page, size)))

    @GetMapping("/accountNumber/{accountNumber}")
    fun getBankByAccountNumber(@PathVariable("accountNumber") accountNumber: Int): ResponseEntity<BankDTO> =
        ResponseEntity.ok(service.getBankByAccountNumber(accountNumber))

    @GetMapping("/owner/{owner}")
    fun getBankByOwner(@PathVariable("owner") owner: String): ResponseEntity<BankDTO> =
        ResponseEntity.ok(service.getBankByOwner(owner))

    @PostMapping
    @Secured("ROLE_ADMIN")
    fun addBank(@RequestBody bank: Bank): ResponseEntity<BankDTO> =
        ResponseEntity.created(getURI(service.addBank(bank).id)).build()

    @Secured("ROLE_ADMIN")
    @PutMapping("/accountNumber/{accountNumber}")
    fun updateBank(@PathVariable accountNumber: Int, @RequestBody bank: Bank): ResponseEntity<BankDTO> =
        ResponseEntity.ok(service.updateBank(accountNumber, bank))

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/accountNumber/{accountNumber}")
    fun deleteBank(@PathVariable accountNumber: Int): ResponseEntity<Unit> {
        service.deleteBank(accountNumber)
        return ResponseEntity.ok().build()
    }
}