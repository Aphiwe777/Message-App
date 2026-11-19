package com.mycompany.messageapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Message class functionality.
 *
 * @author Student
 */
public class MessageTest {

    private final Message messageObj = new Message();

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
        assertEquals("12:0:HITONIGHT", hash);
    }

    @Test
    public void testMessageIDCreated() {
        String id = messageObj.generateMessageID();
        assertNotNull(id);
        assertEquals(10, id.length());
        assertTrue(messageObj.checkMessageID(id));
    }

    @Test
    public void testSendMessageActions() {
        assertEquals("Message successfully sent", messageObj.sendMessage(1));
        assertEquals("Press 0 to delete the message", messageObj.sendMessage(2));
        assertEquals("Message successfully stored", messageObj.sendMessage(3));
    }
}