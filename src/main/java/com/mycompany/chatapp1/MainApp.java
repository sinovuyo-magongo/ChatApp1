/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import com.mycompany.chatapp1.Message;
import java.util.Scanner;

/**
 * Main entry point for the QuickChat application.
 * Handles registration, login, and the message sending menu.
 */
public class MainApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        // ================= REGISTRATION =================
        System.out.println("===== USER REGISTRATION =====");

        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Enter Username (must contain _ and be max 5 chars): ");
        String username = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        System.out.print("Enter Cell Number (e.g. +27831234567): ");
        String cellNumber = input.nextLine();

        String registrationMessage = login.registerUser(firstName, lastName, username, password, cellNumber);
        System.out.println(registrationMessage);

        if (!registrationMessage.equals("User registered successfully.")) {
            input.close();
            return;
        }

        // ================= LOGIN =================
        System.out.println("\n===== USER LOGIN =====");

        System.out.print("Enter Username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter Password: ");
        String loginPassword = input.nextLine();

        String loginMessage = login.loginUser(loginUsername, loginPassword);
        System.out.println(loginMessage);

        if (!login.isLoggedIn()) {
            input.close();
            return;
        }

    }
    
}