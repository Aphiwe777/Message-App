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

    public static class MessageRecord {
        private final String messageID;
        private final String messageHash;
        private final String sender;
        private final String recipient;
        private final String messageText;
        private final String status;

        public MessageRecord(String messageID, String messageHash, String sender,
                             String recipient, String messageText, String status) {
            this.messageID = messageID;
            this.messageHash = messageHash;
            this.sender = sender;
            this.recipient = recipient;
            this.messageText = messageText;
            this.status = status;
        }

        public String getMessageID() {
            return messageID;
        }

        public String getMessageHash() {
            return messageHash;
        }

        public String getSender() {
            return sender;
        }

        public String getRecipient() {
            return recipient;
        }

        public String getMessageText() {
            return messageText;
        }

        public String getStatus() {
            return status;
        }
    }

    private final List<String> sentMessages = new ArrayList<>();
    private final List<String> disregardedMessages = new ArrayList<>();
    private final List<String> storedMessages = new ArrayList<>();
    private final List<String> messageHashes = new ArrayList<>();
    private final List<String> messageIDs = new ArrayList<>();
    private final List<MessageRecord> allRecords = new ArrayList<>();

    private int totalMessagesSent = 0;

    // Check if message ID is not more than 10 characters
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }

    // Check message length (should not exceed 250 characters)
    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }

    // Check recipient cell number starts with '+' and has valid length
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

    // Process and record a message into the appropriate collections and JSON file if stored
    public void processMessage(int actionChoice, String messageID, String messageHash,
                               String sender, String recipient, String messageText) {
        messageIDs.add(messageID);
        messageHashes.add(messageHash);

        if (actionChoice == 1) {
            sentMessages.add(messageText);
            allRecords.add(new MessageRecord(messageID, messageHash, sender, recipient, messageText, "Sent"));
        } else if (actionChoice == 2) {
            disregardedMessages.add(messageText);
            allRecords.add(new MessageRecord(messageID, messageHash, sender, recipient, messageText, "Disregard"));
        } else if (actionChoice == 3) {
            storedMessages.add(messageText);
            allRecords.add(new MessageRecord(messageID, messageHash, sender, recipient, messageText, "Stored"));
            storeMessage(messageID, messageHash, sender, recipient, messageText);
        }
    }

    // Store message details to messages.json
    public boolean storeMessage(String messageID, String messageHash, String sender,
                                String recipient, String messageText) {
        String jsonEntry = String.format(
                "{\"messageID\": \"%s\", \"messageHash\": \"%s\", \"sender\": \"%s\", \"recipient\": \"%s\", \"message\": \"%s\"}",
                escapeJson(messageID),
                escapeJson(messageHash),
                escapeJson(sender),
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

    // Read stored messages from messages.json into an array/list
    public String[] loadStoredMessagesFromJson() {
        List<String> loadedMessages = new ArrayList<>();
        File file = new File("messages.json");
        if (!file.exists()) {
            return new String[0];
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String msg = extractJsonField(line, "message");
                if (msg != null) {
                    loadedMessages.add(msg);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading messages.json: " + e.getMessage());
        }

        storedMessages.clear();
        storedMessages.addAll(loadedMessages);
        return loadedMessages.toArray(new String[0]);
    }

    // 2a. Display sender and recipient of all stored messages
    public String getStoredSendersAndRecipients() {
        StringBuilder sb = new StringBuilder();
        for (MessageRecord record : allRecords) {
            if ("Stored".equalsIgnoreCase(record.getStatus())) {
                sb.append("Sender: ").append(record.getSender())
                  .append(" | Recipient: ").append(record.getRecipient()).append("\n");
            }
        }
        return sb.length() == 0 ? "No stored messages found." : sb.toString().trim();
    }

    // 2b. Display the longest stored message
    public String getLongestStoredMessage() {
        String longest = "";
        for (MessageRecord record : allRecords) {
            if ("Stored".equalsIgnoreCase(record.getStatus()) && record.getMessageText().length() > longest.length()) {
                longest = record.getMessageText();
            }
        }
        return longest.isEmpty() ? "No stored messages found." : longest;
    }

    // 2c. Search for a message ID and return corresponding recipient and message
    public String searchByMessageID(String targetID) {
        for (MessageRecord record : allRecords) {
            if (record.getMessageID().equals(targetID)) {
                return "Recipient: " + record.getRecipient() + " | Message: " + record.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    // 2d. Search for all messages (sent or stored) for a particular recipient
    public String searchMessagesByRecipient(String targetRecipient) {
        List<String> matches = new ArrayList<>();
        for (MessageRecord record : allRecords) {
            if (record.getRecipient().equals(targetRecipient)
                    && ("Sent".equalsIgnoreCase(record.getStatus()) || "Stored".equalsIgnoreCase(record.getStatus()))) {
                matches.add(record.getMessageText());
            }
        }
        return matches.isEmpty() ? "No messages found for recipient: " + targetRecipient : String.join(", ", matches);
    }

    // 2e. Delete a message using the message hash
    public String deleteMessageByHash(String targetHash) {
        for (int i = 0; i < allRecords.size(); i++) {
            MessageRecord record = allRecords.get(i);
            if (record.getMessageHash().equalsIgnoreCase(targetHash)) {
                String removedText = record.getMessageText();
                if ("Sent".equalsIgnoreCase(record.getStatus()) && totalMessagesSent > 0) {
                    totalMessagesSent--;
                }
                sentMessages.remove(removedText);
                storedMessages.remove(removedText);
                disregardedMessages.remove(removedText);
                messageHashes.remove(record.getMessageHash());
                messageIDs.remove(record.getMessageID());
                allRecords.remove(i);
                return "Message: \"" + removedText + "\" successfully deleted.";
            }
        }
        return "No message found with hash: " + targetHash;
    }

    // 2f. Display report listing full details of sent/stored messages
    public String displayMessageReport() {
        StringBuilder sb = new StringBuilder();
        for (MessageRecord record : allRecords) {
            if ("Sent".equalsIgnoreCase(record.getStatus()) || "Stored".equalsIgnoreCase(record.getStatus())) {
                sb.append("Status: ").append(record.getStatus())
                  .append(" | Message Hash: ").append(record.getMessageHash())
                  .append(" | Recipient: ").append(record.getRecipient())
                  .append(" | Message: ").append(record.getMessageText()).append("\n");
            }
        }
        return sb.length() == 0 ? "No messages available for report." : sb.toString().trim();
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

    public String[] getSentMessagesArray() {
        return sentMessages.toArray(new String[0]);
    }

    public String[] getDisregardedMessagesArray() {
        return disregardedMessages.toArray(new String[0]);
    }

    public String[] getMessageHashesArray() {
        return messageHashes.toArray(new String[0]);
    }

    public String[] getMessageIDsArray() {
        return messageIDs.toArray(new String[0]);
    }

    // Generate a random 10-digit message ID
    public String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(id);
    }

    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String extractJsonField(String jsonLine, String fieldName) {
        String key = "\"" + fieldName + "\": \"";
        int start = jsonLine.indexOf(key);
        if (start == -1) {
            return null;
        }
        start += key.length();
        StringBuilder value = new StringBuilder();
        boolean escaped = false;
        for (int i = start; i < jsonLine.length(); i++) {
            char c = jsonLine.charAt(i);
            if (escaped) {
                value.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (c == '"') {
                break;
            } else {
                value.append(c);
            }
        }
        return value.toString();
    }
}