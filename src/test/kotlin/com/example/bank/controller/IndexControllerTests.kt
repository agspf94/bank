package com.example.bank.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest
class IndexControllerTests {
    @Autowired
    private lateinit var indexController: IndexController

    @Nested
    @DisplayName("get()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Get {
        @Test
        fun `should get`() {
            // when
            assertEquals(HttpStatus.OK, indexController.getIndex().statusCode)
            assertEquals("Welcome to our Bank!", indexController.getIndex().body?.message)
        }
    }
}