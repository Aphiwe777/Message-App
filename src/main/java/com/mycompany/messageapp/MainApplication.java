/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.messageapp;

import java.util.Scanner;


public class MainApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MessageApp loginSystem = new MessageApp();

        System.out.println("WELCOME TO IMESSAGE");

        // STEP 1 - REGISTRATION
        System.out.println("SIGN UP");

        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();

        String username;
        while (true) {
            System.out.println("Enter username (Must contain: 1. '_' underscore, 2. five characters or fewer):");
            username = scanner.nextLine();
            if (loginSystem.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username incorrectly formatted, please try again.");
            }
        }

        String password;
        while (true) {
            System.out.println("Enter password. Must contain: "
                    + "1. >= 8 characters, "
                    + "2. uppercase letter, "
                    + "3. number, "
                    + "4. a special character");
            password = scanner.nextLine();
            if (checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured");
                break;
            } else {
                System.out.println("Password inaccurately formatted, please try again.");
            }
        }

        System.out.println("Enter your phone number:");
        String phoneNumber = scanner.nextLine();

        String registrationResult = loginSystem.registerUser(password, phoneNumber, firstName, lastName, username);
        System.out.println("\n" + registrationResult + "\n");

        // STEP 2 - LOGIN
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
            scanner.close();
            return;
        }

        // STEP 3 - PART 2 QUICKCHAT MENU
        System.out.println("\nWelcome to QuickChat.");

        Message msgFeature = new Message();
        boolean running = true;

        while (running) {
            System.out.println("\nQUICKCHAT MENU");
            System.out.println("1 - Send Messages");
            System.out.println("2 - Show recently sent messages");
            System.out.println("3 - Quit");
            System.out.println("Choose an option:");

            int choice = readInt(scanner);

            if (choice == 1) {
                System.out.print("How many messages would you like to enter? ");
                int numMessages = readInt(scanner);

                if (numMessages <= 0) {
                    System.out.println("Please enter a positive number of messages.");
                    continue;
                }

                int msgIndex = 0;
                while (msgIndex < numMessages) {
                    System.out.println("\n--- Entering Message " + (msgIndex + 1) + " of " + numMessages + " ---");

                    System.out.print("Enter the recipient cell number: ");
                    String recipient = scanner.nextLine();
                    if (!msgFeature.checkRecipientCell(recipient)) {
                        System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        continue;
                    }

                    System.out.print("Enter message (MAX 250 characters): ");
                    String messageText = scanner.nextLine();
                    if (!msgFeature.checkMessageLength(messageText)) {
                        int excess = messageText.length() - 250;
                        System.out.println("Message exceeds 250 characters by " + excess + ", please reduce size.");
                        continue;
                    }
                    System.out.println("Message ready to send.");

                    String messageID = msgFeature.generateMessageID();
                    String messageHash = msgFeature.createMessageHash(messageID, msgIndex, messageText);

                    System.out.println("Choose action: 1-Send Message, 2-Disregard Message, 3-Store Message");
                    int actionChoice = readInt(scanner);

                    String actionResult = msgFeature.sendMessage(actionChoice);
                    System.out.println(actionResult);

                    if (actionChoice == 1 || actionChoice == 3) {
                        msgFeature.recordMessage(actionChoice, messageID, messageHash, recipient, messageText);
                    }

                    if (actionChoice == 1) {
                        System.out.println("\n[Message Details Summary]");
                        System.out.println("Message ID: " + messageID);
                        System.out.println("Message Hash: " + messageHash);
                        System.out.println("Recipient: " + recipient);
                        System.out.println("Message: " + messageText);
                    }

                    msgIndex++;
                }

                System.out.println("\nTotal messages sent: " + msgFeature.returnTotalMessages());

            } else if (choice == 2) {
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                System.out.println("Exiting application. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please select 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number:");
            scanner.nextLine();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume trailing newline
        return value;
    }

    // Helper method placed outside of main() inside MainApplication class
    public static boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false;
        boolean hasNum = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasNum = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasNum && hasSpecial;
    }
}