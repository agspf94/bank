package com.example.bank.service

import com.example.bank.entity.Bank
import com.example.bank.entity.BankDTO
import com.example.bank.exception.exceptions.AccountNumberNotFoundException
import com.example.bank.exception.exceptions.OwnerNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest

@SpringBootTest
class BankServiceTests {
    @Autowired
    private lateinit var service: BankService

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should getBanks`() {
            // given
            val banks: List<BankDTO> = service.getBanks(PageRequest.of(0, 4))

            // when
            assertEquals(4, banks.size)

            val bank1 = banks[0]
            assertEquals(1, bank1.id)
            assertEquals(1, bank1.accountNumber)
            assertEquals("Anderson", bank1.owner)
            assertEquals(100.0, bank1.balance)

            val bank2 = banks[1]
            assertEquals(2, bank2.id)
            assertEquals(2, bank2.accountNumber)
            assertEquals("Giuseppe", bank2.owner)
            assertEquals(100.0, bank2.balance)

            val bank3 = banks[2]
            assertEquals(3, bank3.id)
            assertEquals(3, bank3.accountNumber)
            assertEquals("Saraiva", bank3.owner)
            assertEquals(100.0, bank3.balance)

            val bank4 = banks[3]
            assertEquals(4, bank4.id)
            assertEquals(4, bank4.accountNumber)
            assertEquals("Patriarca", bank4.owner)
            assertEquals(100.0, bank4.balance)
        }
    }

    @Nested
    @DisplayName("getBankByAccountNumber()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBankDTOById {
        @Test
        fun `should getBankByAccountNumber`() {
            // given
            val existing: BankDTO = service.getBankByAccountNumber(1)

            // when
            assertEquals(1, existing.id)
            assertEquals(1, existing.accountNumber)
            assertEquals("Anderson", existing.owner)
            assertEquals(100.0, existing.balance)
        }

        @Test
        fun `should getBankByAccountNumber unsuccessfully`() {
            assertThrows(AccountNumberNotFoundException::class.java) { service.getBankByAccountNumber(10) }
        }
    }

    @Nested
    @DisplayName("getBankByOwner()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBankDTOByOwner {
        @Test
        fun `should getBankDTOByOwner`() {
            // given
            val existing: BankDTO = service.getBankByOwner("Anderson")

            // when
            assertEquals(1, existing.id)
            assertEquals(1, existing.accountNumber)
            assertEquals("Anderson", existing.owner)
            assertEquals(100.0, existing.balance)
        }

        @Test
        fun `should getBankDTOByOwner unsuccessfully`() {
            assertThrows(OwnerNotFoundException::class.java) { service.getBankByOwner("Fantin") }
        }
    }

    @Nested
    @DisplayName("addBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBank {
        @Test
        fun `should addBank`() {
            // given
            val bank = Bank(5, 5, "Fantin", 100.0, "1005")

            // when
            var bankDTO: BankDTO = service.addBank(bank)
            assertNotNull(bankDTO)
            assertNotNull(bankDTO.id)

            val optional: BankDTO = service.getBankByAccountNumber(bankDTO.accountNumber)
            assertNotNull(optional)

            bankDTO = optional
            assertEquals(5, bankDTO.accountNumber)
            assertEquals("Fantin", bankDTO.owner)
            assertEquals(100.0, bankDTO.balance)

            // then
            service.deleteBank(bank.accountNumber)
        }
    }

    @Nested
    @DisplayName("updateBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateBankById {
        @Test
        fun `should updateBank`() {
            // given
            val bankDTO: BankDTO = service.addBank(Bank(5, 5, "Fantin", 100.0, "1005"))

            // when
            assertEquals(100.0, bankDTO.balance)
            val bankDTOUpdated = service.updateBank(bankDTO.accountNumber, Bank(5, 5, "Fantin", 110.0, "1005"))
            assertEquals(110.0, bankDTOUpdated.balance)

            // then
            service.deleteBank(bankDTO.accountNumber)
        }
    }

    @Nested
    @DisplayName("deleteBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {
        @Test
        fun `should deleteBank`() {
            // given
            val bank = Bank(5, 5, "Fantin", 100.0, "1005")
            service.addBank(bank)

            // when
            service.deleteBank(bank.accountNumber)

            // then
            assertThrows(AccountNumberNotFoundException::class.java) { service.deleteBank(10) }
        }

        @Test
        fun `should deleteBankByAccountNumber unsuccessfully`() {
            assertThrows(AccountNumberNotFoundException::class.java) { service.deleteBank(10) }
        }
    }
}