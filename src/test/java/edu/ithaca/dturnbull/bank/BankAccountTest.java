package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address (Equivalence class: valid email)
        assertFalse(BankAccount.isEmailValid(""));         // empty string (Equivalence class: empty string)
        assertFalse(BankAccount.isEmailValid("a-@mail.com"));   // dash before @ (Equivalence class: character placement)
        assertFalse(BankAccount.isEmailValid("ab@g#mail.com")); // invalid character # (Equivalence class: invalid characters)
        assertFalse(BankAccount.isEmailValid("a@b.c"));        // no dots in domain (Equivalence class: domain format) This is a border case as some domains have only two letters
// Another border case we could have is that there is null input
        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}