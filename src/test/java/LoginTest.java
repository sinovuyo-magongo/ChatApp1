/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.chatapp1;
import cn.org.faster.framework.auth.annotation.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Login class.
 * Tests username validation, password complexity, and phone number format.
 */
public class LoginTest {

    Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
    }

    // ---------------- USERNAME TESTS ----------------

    @Test
    public void testValidUsername() {
        // Valid: contains underscore and is 5 chars or less
        assertTrue(login.checkUserName("Sinovuyo"));
    }

    @Test
    public void testInvalidUsername_NoUnderscore() {
        // No underscore - should be false
        assertFalse(login.checkUserName("Sinov"));
    }

    @Test
    public void testInvalidUsername_TooLong() {
        // More than 5 characters - should be false
        assertFalse(login.checkUserName("si_noo"));
    }

    // ------------- PASSWORD TESTS --------------

    @Test
    public void testValidPassword() {
        // Valid: 8+ chars, capital, number, special
        assertTrue(login.checkPasswordComplexity("Maqo@n55"));
    }

    @Test
    public void testInvalidPassword_TooShort() {
        // Less than 8 characters - should be false
        assertFalse(login.checkPasswordComplexity("Mq@1"));
    }

    @Test
    public void testInvalidPassword_NoCapitalLetter() {
        // No capital letter - should be false
        assertFalse(login.checkPasswordComplexity("maqo@n55"));
    }

    @Test
    public void testInvalidPassword_NoNumber() {
        // No number - should be false
        assertFalse(login.checkPasswordComplexity("Maqo@nnn"));
    }

    @Test
    public void testInvalidPassword_NoSpecialChar() {
        // No special character - should be false
        assertFalse(login.checkPasswordComplexity("Maqon555"));
    }

    // -------------- PHONE NUMBER TESTS --------------

    @Test
    public void testValidPhoneNumber() {
        // Valid SA number starting with +27 and exactly 12 chars
        assertTrue(login.checkPhoneNumber("+27749802214"));
    }

    @Test
    public void testInvalidPhoneNumber_MissingPlusCode() {
        // Missing +27 - should be false
        assertFalse(login.checkPhoneNumber("0749802214"));
    }

    @Test
    public void testInvalidPhoneNumber_TooShort() {
        // Too short - should be false
        assertFalse(login.checkPhoneNumber("+27740221"));
    }
}
