/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.messageapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */

    
    public class MessageReportTest {

    private Message msgSystem;
    private String msg1Id;
    private String msg2Id;
    private String msg3Id;
    private String msg4Id;
    private String msg5Id;
    private String msg2Hash;
    
    @BeforeEach
    public void setUp() {
        msgSystem = new Message();

        msg1Id = "1000000001";
        String msg1Text = "Did you get the cake?";
        String msg1Hash = msgSystem.createMessageHash(msg1Id, 0, msg1Text);
        msgSystem.sendMessage(1);
        msgSystem.processMessage(1, msg1Id, msg1Hash, "developer", "+27834557896", msg1Text);

        msg2Id = "1000000002";
        String msg2Text = "Where are you? You are late! I have asked you to be on time.";
        msg2Hash = msgSystem.createMessageHash(msg2Id, 1, msg2Text);
        msgSystem.sendMessage(3);
        msgSystem.processMessage(3, msg2Id, msg2Hash, "developer", "+27838884567", msg2Text);

        msg3Id = "1000000003";
        String msg3Text = "Yohoooo, I am at your gate.";
        String msg3Hash = msgSystem.createMessageHash(msg3Id, 2, msg3Text);
        msgSystem.sendMessage(2);
        msgSystem.processMessage(2, msg3Id, msg3Hash, "developer", "+27834484567", msg3Text);

        msg4Id = "1000000004";
        String msg4Text = "It is dinner time !";
        String msg4Hash = msgSystem.createMessageHash(msg4Id, 3, msg4Text);
        msgSystem.sendMessage(1);
        msgSystem.processMessage(1, msg4Id, msg4Hash, "0838884567", "+27838884567", msg4Text);

        msg5Id = "1000000005";
        String msg5Text = "Ok, I am leaving without you.";
        String msg5Hash = msgSystem.createMessageHash(msg5Id, 4, msg5Text);
        msgSystem.sendMessage(3);
        msgSystem.processMessage(3, msg5Id, msg5Hash, "developer", "+27838884567", msg5Text);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        String[] sent = msgSystem.getSentMessagesArray();
        assertEquals(2, sent.length);
        assertEquals("Did you get the cake?", sent[0]);
        assertEquals("It is dinner time !", sent[1]);
    }

    @Test
    public void testDisplayLongestMessage() {
        String longest = msgSystem.getLongestStoredMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        String result = msgSystem.searchByMessageID(msg4Id);
        assertEquals("Recipient: +27838884567 | Message: It is dinner time !", result);
    }

   @Test
    public void testSearchMessagesByRecipient() {
        String result = msgSystem.searchMessagesByRecipient("+27838884567");
        assertEquals(
                "Where are you? You are late! I have asked you to be on time., It is dinner time !, Ok, I am leaving without you.",
                result
        );
    }

    @Test
    public void testDeleteMessageByHash() {
        String result = msgSystem.deleteMessageByHash(msg2Hash);
        assertEquals(
                "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                result
        );
    }

    @Test
    public void testDisplayMessageReport() {
        String report = msgSystem.displayMessageReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
    }
    } 
    
    

