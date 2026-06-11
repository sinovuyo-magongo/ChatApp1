/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp1;

public class Login {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellNumber;
    private boolean loggedIn = false;

    // ---------- Registration ----------

    /**
     * Registers the user. Validates username, password, and cell number.
     * Returns a success or failure message.
     */
    public String registerUser(String firstName, String lastName, String username, String password, String cellNumber) {
        this.firstName = firstName;
        this.lastName = lastName;

        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an " +
                   "underscore and is no more than 5 characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least " +
                   "8 characters, a capital letter, a number, and a special character.";
        }

        if (!checkPhoneNumber(cellNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. " +
                   "Please correct the number and try again.";
        }

        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        return "User registered successfully.";
    }

    /**
     * Username must contain an underscore and be no more than 5 characters.
     * Returns true if valid, false otherwise.
     */
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Password must be at least 8 characters, contain a capital letter,
     * a number, and a special character.
     * Returns true if valid, false otherwise.
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasCapital = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasCapital && hasDigit && hasSpecial;
    }

    /**
     * Cell number must start with +27 and be exactly 12 characters long.
     * Returns true if valid, false otherwise.
     */
    public boolean checkPhoneNumber(String cellNumber) {
        if (cellNumber == null) return false;
        return cellNumber.startsWith("+27") && cellNumber.length() == 12;
    }

    /**
     * Logs the user in. Returns a personalised success or failure message.
     */
    public String loginUser(String username, String password) {
        if (this.username == null || this.password == null) {
            return "Username or password incorrect, please try again.";
        }
        if (this.username.equals(username) && this.password.equals(password)) {
            loggedIn = true;
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
