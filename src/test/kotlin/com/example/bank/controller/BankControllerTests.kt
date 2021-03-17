package com.example.bank.controller

import com.example.bank.BankApplication
import com.example.bank.entity.Bank
import com.example.bank.entity.BankDTO
import com.example.bank.exception.exceptions.AccountNumberNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.test.context.support.WithUserDetails


@SpringBootTest(classes = [BankApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankControllerTests {
    @Autowired
    private lateinit var controller: BankController

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    private fun getRestTemplateAuth() = restTemplate.withBasicAuth("admin", "admin")

    private fun getBanks(): ResponseEntity<List<BankDTO>> {
        return try {
            getRestTemplateAuth()
                .exchange("/api/banks", HttpMethod.GET, null, object: ParameterizedTypeReference<List<BankDTO>>(){})
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.notFound().build()
        }
    }

    private fun getBank(url: String): ResponseEntity<BankDTO> {
        return try {
            getRestTemplateAuth()
                .getForEntity(url, BankDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.notFound().build()
        }
    }

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should getBanks`() {
            // given
            val banks = getBanks().body

            // when
            assertNotNull(banks)
            assertEquals(4, banks?.size)
        }
    }

    @Nested
    @DisplayName("getBankByAccountNumber()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBankByAccountNumber {
        @Test
        fun `should getBankByAccountNumber`() {
            // when
            assertEquals(HttpStatus.OK, getBank("/api/banks/accountNumber/1").statusCode)
        }

        @Test
        fun `should getBankByAccountNumber unsuccessfully`() {
            // when
            assertEquals(HttpStatus.NOT_FOUND, getBank("/api/banks/accountNumber/10").statusCode)
        }
    }

    @Nested
    @DisplayName("getBankByOwner")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBankByOwner {
        @Test
        fun `should getBankByOwner`() {
            // when
            assertEquals(HttpStatus.OK, getBank("/api/banks/owner/Anderson").statusCode)
        }

        @Test
        fun `should getBankByOwner unsuccessfully`() {
            // when
            assertEquals(HttpStatus.NOT_FOUND, getBank("/api/banks/owner/Andersom").statusCode)
        }
    }

    @Nested
    @DisplayName("addBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithUserDetails("admin")
    inner class AddBank {
        @Test
        fun `should addBank`() {
            // given
            val response: ResponseEntity<BankDTO> =
                getRestTemplateAuth()
                    .postForEntity("/api/banks", Bank(5, 5, "Fantin", 100.0, "1005"), null)

            // when
            assertEquals(HttpStatus.CREATED, response.statusCode)

            // then
            val location: String = response.headers.location.toString()
            getRestTemplateAuth()
                .delete(location)
            controller.deleteBank(5)
        }

        @Test
        fun `should addBank unsuccessfully`() {
            // given
            val response: ResponseEntity<BankDTO> =
                getRestTemplateAuth()
                    .postForEntity("/api/banks", Bank(4, 4, "Patriarca", 100.0, "1004"), null)

            // when
            assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        }
    }

    @Nested
    @DisplayName("updateBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithUserDetails("admin")
    inner class UpdateBank {
        @Test
        fun `should updateBank`() {
            // given
            val response: ResponseEntity<BankDTO> = controller.updateBank(4, Bank(4, 4, "Patriarca", 110.0, "1004"))

            // when
            assertEquals(HttpStatus.OK, response.statusCode)

            // then
            controller.updateBank(4, Bank(4, 4, "Patriarca", 100.0, "1004"))
        }

        @Test
        fun `should updateBank unsuccessfully`() {
            // given
            try {
                controller.updateBank(10, Bank(5, 10, "Fantin", 110.0, "1005"))
                fail()
            } catch (e: AccountNumberNotFoundException) {
                assertTrue(true)
            }
        }
    }

    @Nested
    @DisplayName("deleteBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithUserDetails("admin")
    inner class DeleteBank {
        @Test
        fun `should deleteBank`() {
            // given
            val temp: ResponseEntity<BankDTO> =
                getRestTemplateAuth()
                    .postForEntity("/api/banks", Bank(5, 5, "Fantin", 100.0, "1005"), null)

            // when
            val response = controller.deleteBank(5)
            assertEquals(HttpStatus.OK, response.statusCode)
        }

        @Test
        fun `should deleteBank unsuccessfully`() {
            // given
            try {
                controller.deleteBank(10)
                fail()
            } catch (e: AccountNumberNotFoundException) {
                assertTrue(true)
            }
        }
    }
}