/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.messageapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {

    private final List<String> sentMessages = new ArrayList<>();
    private final List<String> storedMessages = new ArrayList<>();
    private int totalMessagesSent = 0;

    // Check if message ID is not more than 10 characters
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }

    // Check message length (should not exceed 250 characters)
    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }

    // Check recipient cell number starts with '+' and is within valid length
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null && cellNumber.startsWith("+") && cellNumber.length() <= 13 && cellNumber.length() >= 10;
    }

    // Autogenerate Message Hash: <first 2 of ID>:<messageNumber>:<FIRSTWORD><LASTWORD> in uppercase
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String prefix = (messageID != null && messageID.length() >= 2)
                ? messageID.substring(0, 2)
                : "00";

        String firstWord = "";
        String lastWord = "";

        if (messageText != null && !messageText.trim().isEmpty()) {
            String[] words = messageText.trim().split("\\s+");
            firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
            lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        }

        return (prefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Handle Send (1), Disregard (2), or Store (3) action
    public String sendMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent";
            case 2:
                return "Press 0 to delete the message";
            case 3:
                return "Message successfully stored";
            default:
                return "Invalid choice";
        }
    }

    // Record a formatted summary for sent or stored messages
    public void recordMessage(int actionChoice, String messageID, String messageHash, String recipient, String messageText) {
        String formatted = "Message ID: " + messageID
                + " | Message Hash: " + messageHash
                + " | Recipient: " + recipient
                + " | Message: " + messageText;

        if (actionChoice == 1) {
            sentMessages.add(formatted);
        } else if (actionChoice == 3) {
            storedMessages.add(formatted);
            storeMessage(messageID, messageHash, recipient, messageText);
        }
    }

    // Return all messages sent while the program is running
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        return String.join("\n", sentMessages);
    }

    // Return total number of messages sent
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Store message details to a JSON file (messages.json)
    public boolean storeMessage(String messageID, String messageHash, String recipient, String messageText) {
        String jsonEntry = String.format(
                "{\"messageID\": \"%s\", \"messageHash\": \"%s\", \"recipient\": \"%s\", \"message\": \"%s\"}",
                escapeJson(messageID),
                escapeJson(messageHash),
                escapeJson(recipient),
                escapeJson(messageText)
        );

        try (PrintWriter out = new PrintWriter(new FileWriter("messages.json", true))) {
            out.println(jsonEntry);
            return true;
        } catch (IOException e) {
            System.out.println("Error storing message to JSON file: " + e.getMessage());
            return false;
        }
    }

    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // Generate a random 10-digit message ID
    public String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(id);
    }
}