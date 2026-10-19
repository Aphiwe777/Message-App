/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.messageapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageAppTest {
    
    
    MessageApp loginSystem = new MessageApp();
    
    //USERNAME accurately formatted = true
    @Test
    public void testCheckUserNameCorrectlyFormatted(){
        boolean result = loginSystem.checkUserName("kyl_1");
        assertTrue(result);       
    }
    
    //USERNAME inaccurately formatted = false
    @Test
    public void testCheckUserNameInaccuratelytFormatted() {
        boolean result = loginSystem.checkUserName("kyle!!!!!!");
        assertFalse(result);
    }
    
    //PASSWORD meets the required conditions = true
    @Test
    public void testCheckPasswordComplexitySuccess() {
        boolean result = loginSystem.checkPasswordComplexity("Ch3&sec@ke99!");
        assertTrue(result);
    }
    
    //PASSWORD does not meet complexity reqiurements = false
    @Test
    public void testCheckPasswordComplexitySucces () {
        boolean result = loginSystem.checkPasswordComplexity("password");
        assertFalse(result);
    }
    
    //CELLPHONE test = true
    @Test
    public void testCellPhoneNumberSuccess () {
        boolean result = loginSystem.checkphoneNumber("+27838968976");
        assertTrue(result);
    }
    
    //CELLPHONE test = false
    @Test
    public void testCellPhoneNumberFailure () {
        boolean result = loginSystem.checkphoneNumber("08966553");
        assertFalse(result);
    }
    
    //LOGIN = SUCCESS/FAILURE
    @Test
    public void testLoginSucces () {
        loginSystem.registerUsername("kyle!!!!!!", "Ch3esec@ke99!", "+27838968976");
        boolean result = loginSystem.checkloginsuccess("correctUser", "correctPass");
        assertTrue(result);
    }
    
    
    
    public MessageApp() {
    }

  
}