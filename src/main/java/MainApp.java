
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainApp loginSystem = new MainApp();
        
        System.out.println("WELCOME TO IMESSAGE");
        
        //STEP 1- REGISTRATION (ask user for names)
        System.out.println("SIGN UP");
        
        System.out.println("Enter first your name");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter your last name");
        String lastName = scanner.nextLine();
        
        //Prompt user for a username and make sure it is properly formatted
        String username;
        while (true) {
            System.out.println("Enter username (Must contain:"
                    + " 1. '_'underscore"
                    + "2. five charactters long)");
            username = scanner.nextLine(); 
            if (loginSystem.checkUserName(username)) {
            System.out.println("Username successfully captured.");
        } else {
                System.out.println("Username incorrectly formatted, please try again.");
        }
        
    }
        
//Prompt user for valid password (must meet conditions)
    String password;
    while (true) {
        System.out.println("Enter password. Must contain: "
                + "1. >= 8 characters"
                + "2. uppercase letter"
                + "3. number"
                + "4. a special character");
        password =scanner.nextLine();
        if (loginSystem.checkPasswordComplexity(password)) {
            System.out.println("Password successfully captured");
        } else{
            System.out.println("Password inaccurately formatted, please try again.");          
        }
        System.out.println("Enter your phone number");
        String phoneNumber = scanner.nextLine();
        
        //Registration
        String registrationResult = loginSystem.registerUser(password, phoneNumber, firstName, lastName, username);
        System.out.println("\n" + registrationResult + "\n");
        
        //login
        System.out.println("LOGIN");
        System.out.println("Enter Your Username: ");
        String loginUsername = scanner.nextLine();
        
        System.out.println("Enter Password: ");
        String loginPassword = scanner.nextLine() ;
        
        boolean isLoggedIn = loginSystem.loginUser(loginUsername, loginPassword);
        String loginMessage = loginSystem.returnLoginStatus(isLoggedIn);
        
        System.out.println("\n" + loginMessage);
        scanner.close();
    }
    }
}
    

