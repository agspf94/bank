package com.example.bank.exception

import java.util.*

data class ErrorDetails(
    val time: Date,
    val message: String,
    val details: String,
)