/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.messageapp;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Message {

    // Check if message ID is not more than 10 characters 
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }
    
    // Check message length (SHOULD NOT EXCEED 250 CHARACTERS)
    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }
    
    // Check recipient cell number format
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null && cellNumber.startsWith("+") && cellNumber.length() >= 10;
    }
    
    
    
    
    
    // MESSAGE HASH (First two sums of ID)
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String idPrefix = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) + "00" : "";
        
        String firstWord = "";
        String lastWord = "";
        if (messageText != null && !messageText.trim().isEmpty()) {
            String[] words = messageText.trim().split("\\s+"); 
            firstWord = words[0].replaceAll("[a-zA-Z]", "");
        }
        
        return idPrefix + ":" + firstWord;
    }
         
    // SEND / STORE / DISREGARD MESSAGES 
    public String sendMessage(int choice) {
        switch (choice) {
            case 1: 
                return "Message successfully sent";
            case 2: 
                return "Press 0 to delete the message";
            case 3: 
                return "Message successfully stored";
            default: 
                return "Invalid choice";
        }
    }

    // Generate a random 10 digit message ID 
    public String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(id);
    }
}