package com.example.bank.exception

import com.example.bank.exception.exceptions.OwnerNotFoundException
import com.example.bank.exception.exceptions.AccountNumberAlreadyExistsException
import com.example.bank.exception.exceptions.AccountNumberNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class ControllerAdvisor : ResponseEntityExceptionHandler() {
    @ExceptionHandler(AccountNumberNotFoundException::class)
    fun handleAccountNumberNotFoundException(e: AccountNumberNotFoundException, request: WebRequest): ResponseEntity<ErrorDetails> =
        ResponseEntity(ErrorDetails(Date(), "AccountNumberNotFoundException", e.message!!), HttpStatus.NOT_FOUND)

    @ExceptionHandler(OwnerNotFoundException::class)
    fun handleOwnerNotFoundException(e: OwnerNotFoundException, request: WebRequest): ResponseEntity<ErrorDetails> =
        ResponseEntity(ErrorDetails(Date(), "OwnerNotFoundException", e.message!!), HttpStatus.NOT_FOUND)

    @ExceptionHandler(AccountNumberAlreadyExistsException::class)
    fun handleAccountNumberAlreadyExistsException(e: AccountNumberAlreadyExistsException, request: WebRequest): ResponseEntity<ErrorDetails> =
        ResponseEntity(ErrorDetails(Date(), "AccountNumberAlreadyExistsException", e.message!!), HttpStatus.BAD_REQUEST)
}