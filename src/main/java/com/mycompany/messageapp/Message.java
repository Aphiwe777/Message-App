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
public class Message {
    //check if message ID is not more than 10 characters 
    public boolean checkMessageID(String messageID) {
      return messageID != null && messageID.length() <=10;
    }
    
    //check message length (SHOULD NOT EXCEED 250 CHARACTERS)
    public boolean checkMessageLength(String message) {
    return message != null && message.length() <=250;
    }
}
