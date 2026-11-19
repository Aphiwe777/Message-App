/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.messageapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for registration, authentication, and validation in MessageApp.
 *
 * @author Student
 */
public class MessageAppTest {

    private final MessageApp loginSystem = new MessageApp();

    // USERNAME accurately formatted = true
    @Test
    public void testCheckUserNameCorrectlyFormatted() {
        boolean result = loginSystem.checkUserName("kyl_1");
        assertTrue(result);
    }

    // USERNAME inaccurately formatted = false
    @Test
    public void testCheckUserNameInaccuratelyFormatted() {
        boolean result = loginSystem.checkUserName("kyle!!!!!!!");
        assertFalse(result);
    }

    // PASSWORD meets the required conditions = true
    @Test
    public void testCheckPasswordComplexitySuccess() {
        boolean result = loginSystem.checkPasswordComplexity("Ch3&sec@ke99!");
        assertTrue(result);
    }

    // PASSWORD does not meet complexity requirements = false
    @Test
    public void testCheckPasswordComplexityFailure() {
        boolean result = loginSystem.checkPasswordComplexity("password");
        assertFalse(result);
    }

    // CELLPHONE test - true
    @Test
    public void testCellPhoneNumberSuccess() {
        boolean result = loginSystem.checkphoneNumber("+27838968976");
        assertTrue(result);
    }

    // CELLPHONE test - false
    @Test
    public void testCellPhoneNumberFailure() {
        boolean result = loginSystem.checkphoneNumber("08966553");
        assertFalse(result);
    }

    // LOGIN success
    @Test
    public void testLoginSuccess() {
        loginSystem.registerUser("Ch3&sec@ke99!", "+27838968976", "Kyle", "Doe", "kyl_1");
        boolean result = loginSystem.loginUser("kyl_1", "Ch3&sec@ke99!");
        assertTrue(result);
    }
}