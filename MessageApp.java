/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.messageapp;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class MessageApp {

    
        private String registeredUsername;
        private String registeredPassword;
        private String registeredphoneNumber;
        private String firstName;
        private String lastName;
        
        //Constructors to initialize
        public MessageApp() {
        }
        public MessageApp(String firstame, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName; 
        }
        
        //METHOD 1 = check username is <= 5 characters and contains underscore
        public boolean checkUserName(String Username) {
        if (Username == null) return false;
        return Username.contains("_") && Username.length() <=5;
        }
        
        //MEHTOD 2 = check password complexity
        public boolean checkPasswordComplexity(String password){
        if (password == null) return false;
        
        boolean isLongEnough = password.length() >= 8;
        boolean hasCapitalLetter = !password.equals(password.toLowerCase()) && password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches (".*[0-9].*");
        boolean hasSpecialCase = password.matches(".*[^a-zA-Z0-9].*");
        
        return isLongEnough && hasCapitalLetter && hasNumber && hasSpecialCase;                     
    }
        //Regex checker for valid South African cell phone number (+27)
        // Refernce prefix (+27) and a boolean to checkPhoneNumber
        public boolean checkphoneNumber (String phoneNumber){
        if (phoneNumber == null) return false;
        // Check that phone number begins with +27, followed by 9 digits
        String regex = "^\\+27[0-9]{9}$";
        return Pattern.matches(regex, phoneNumber);
}
        //Register the user and return a status message to confirm/deny
        public String registerUser (String username, String password, String cellNumber, String firstName, String lastName) {
            if (!checkUserName(username)){
                return "Username is not correctly formatted, please ensure that your "
                       + "username contains an underscore and is no more than 5 characters long ";
            }
            if (!checkPasswordComplexity(password)){
                return "Password is  ot correctly formatted, please ensure that "
                        + "the password contains a minimum of 8 characters, a captal letter "
                        + "and a special character.";
            }
            if (!checkphoneNumber(cellNumber)){
                return "Phone number incorrectly formatted, please ensure"
                        + "the international code is included";
                
            }
                //store details
                this.registeredPassword = password;
                this.registeredUsername = username;
                this.registeredphoneNumber = cellNumber; 
                
                return "Username successfully captured";                    
            
            }
            
            //compare registred credentials to see if theymatch with entered credntials
            
            public boolean loginUser (String username, String password){
            if (this.registeredUsername == null || this.registeredPassword == null){
            
           }
            return this.registeredUsername.equals(username) && this.registeredPassword.equals(password);

        }

         //Return message for login status
public String returnLoginStatus(boolean isLoggedIn){
    if (isLoggedIn) {
        return "Welcome " + firstName + ", " + lastName + " it's great to see you again.";
    } else {
        return "Username or password incorrect, please try again.";
    }
}
  

//elements for testing 
     public void setRegisteredUsername(String registerdUsername){
         this.registeredUsername = registeredUsername;
     }  
      public void setRegisteredPassword(String registeredPassword) {
          this.registeredPassword = registeredPassword;
      }
      public void setfirstName(String firstName) {
          this.firstName = firstName;
      }
      public void setLastName(String lastName) {
    }
    }

