/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.messageapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    Message messageObj = new Message();

    @Test
    public void testMessageLengthSuccess() {
        boolean result = messageObj.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertTrue(result);
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "A".repeat(260); // Exceeds 250 characters
        boolean result = messageObj.checkMessageLength(longMsg);
        assertFalse(result);
    }

    @Test
    public void testRecipientCellSuccess() {
        boolean result = messageObj.checkRecipientCell("+27718693002");
        assertTrue(result);
    }

    @Test
    public void testRecipientCellFailure() {
        boolean result = messageObj.checkRecipientCell("12345");
        assertFalse(result);
    }

    @Test
    public void testMessageHashCreation() {
        String hash = messageObj.createMessageHash("1234567890", 0, "Hi Mike, can you join us for dinner tonight?");
        assertTrue(hash.contains("1200:"));
    }
}
    

