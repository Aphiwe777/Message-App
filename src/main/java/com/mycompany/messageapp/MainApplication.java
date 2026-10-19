/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.messageapp;

import java.util.Scanner;

/**
 *
 * @author Student
 */

public class MainApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MessageApp loginSystem = new MessageApp();
        
        System.out.println("WELCOME TO IMESSAGE");
        
        // STEP 1 - REGISTRATION (ask user for names)
        System.out.println("SIGN UP");
        
        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();
        
        // Prompt user for a username and make sure it is properly formatted
        String username;
        while (true) {
            System.out.println("Enter username (Must contain: 1. '_' underscore, 2. five characters long):");
            username = scanner.nextLine(); 
            if (loginSystem.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break; // Exit loop once valid
            } else {
                System.out.println("Username incorrectly formatted, please try again.");
            }
        }
        
        // Prompt user for valid password (must meet conditions)
        String password;
        while (true) {
            System.out.println("Enter password. Must contain: "
                    + "1. >= 8 characters, "
                    + "2. uppercase letter, "
                    + "3. number, "
                    + "4. a special character");
            password = scanner.nextLine();
            if (loginSystem.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured");
                break; // Exit password loop once valid
            } else {
                System.out.println("Password inaccurately formatted, please try again.");          
            }
        }

        System.out.println("Enter your phone number:");
        String phoneNumber = scanner.nextLine();
        
        // Registration execution
        String registrationResult = loginSystem.registerUser(password, phoneNumber, firstName, lastName, username);
        System.out.println("\n" + registrationResult + "\n");
        
        // Login sequence
        System.out.println("LOGIN");
        System.out.println("Enter Your Username: ");
        String loginUsername = scanner.nextLine();
        
        System.out.println("Enter Password: ");
        String loginPassword = scanner.nextLine();
        
        boolean isLoggedIn = loginSystem.loginUser(loginUsername, loginPassword);
        String loginMessage = loginSystem.returnLoginStatus(isLoggedIn);
        
        System.out.println("\n" + loginMessage);
        
        if (!isLoggedIn) {
            System.out.println("Log in failed. Exiting the application");
            return;
        }
        
        // ---------- PART 2 --- QUICK CHAT MENU ------------ //
        System.out.println("Welcome to Quickchat.");
        
        boolean running = true;
        int totalMessageSent = 0;
        
        while (running) {
            System.out.println("\nQUICKCHAT MENU");
            System.out.println("1 - Send Message");
            System.out.println("2 - Show recently sent messages");
            System.out.println("3 - Quit");
            System.out.println("Choose an option:");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline 
            
            if (choice == 1) {
                System.out.print("How many messages would you like to enter? ");
                int numMessages = scanner.nextInt();
                scanner.nextLine();
                
                Message msgFeature = new Message();
                
                for (int i = 0; i < numMessages; i++) {
                    System.out.println("\n--- Entering Message " + (i + 1) + " of " + numMessages + " ---");
                    
                    System.out.print("Enter the recipient cell number: ");
                    String recipient = scanner.nextLine();
                    if (!msgFeature.checkRecipientCell(recipient)) {
                        System.out.println("Cell phone number is incorrectly formatted "
                                + "or does not contain an international code. "
                                + "Please make sure you have typed in the number correctly and try again.");
                        i--; // Re-try message iteration
                        continue;
                    }
                    
                    System.out.println("Enter message (MAX-250 characters): ");
                    String messageText = scanner.nextLine();
                    
                    if (!msgFeature.checkMessageLength(messageText)) {
                        int excess = messageText.length() - 250;
                        System.out.println("Please enter a message of less than 250 characters. (Exceeds by " + excess + ")");
                        i--; // Re-try message iteration 
                        continue; 
                    } else {
                        System.out.println("Message is ready to send.");
                    }
                    
                    String messageID = msgFeature.generateMessageID();
                    String messageHash = msgFeature.createMessageHash(messageID, i, messageText);
                    
                    System.out.println("Choose action: 1-Send Message, 2-Disregard Message, 3-Store Message ");
                    int actionChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String actionResult = msgFeature.sendMessage(actionChoice);
                    System.out.println(actionResult);

                    if (actionChoice == 1) {
                        totalMessageSent++;
                    }

                    // Display summary 
                    System.out.println("\n[Message Details Summary]");
                    System.out.println("Message ID: " + messageID);
                    System.out.println("Message Hash: " + messageHash);
                    System.out.println("Recipient: " + recipient);
                    System.out.println("Message: " + messageText);
                }

                System.out.println("\nTotal messages sent / processed: " + totalMessageSent);

            } else if (choice == 2) {
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                System.out.println("Exiting application. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please select 1, 2, or 3.");
            }
        }
    }
}

