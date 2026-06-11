/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.chatapp1;

import java.util.Random;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single QuickChat message.
 * Manages message validation, hashing, sending, storing, and discarding.
 */
public class Message {

    // ---------- Static counters and total ----------
    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;

    public static List<String> getStoredMessages() {
        return storedMessages;
    }

    // ---------- Instance fields ----------
    private int messageNumber;
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    private static List<String> sentMessages        = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();
    private static List<String> storedMessages      = new ArrayList<>();
    private static List<String> messageHashes       = new ArrayList<>();
    private static List<String> messageIDs          = new ArrayList<>();
    private static List<String> recipientList       = new ArrayList<>();
    // ---------- Constructors ----------
    /**
     * Creates a new Message and assigns it the next message number.
     */
    public Message() {
        messageCounter++;
        this.messageNumber = messageCounter;
        this.messageID = generateMessageID();
    }

    // ---------- ID Generation ----------

    /**
     * Generates a random 10-digit message ID.
     */
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // ---------- Validation ----------

    /**
     * Validates that the message ID is exactly 10 digits.
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    /**
     * Validates the message length is within 250 characters.
     * Returns a descriptive result string.
     */
    public String checkMessageLength() {
        if (messageText == null) return "Message exceeds 250 characters by 0; please reduce the size.";
        int length = messageText.length();
        if (length <= 250) {
            return "Message ready to send.";
        } else {
            int over = length - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
    }

    /**
     * Static version of message length validation used in MainApp.
     */
    public static String validateMessageLength(String text) {
        if (text == null || text.length() > 250) return "Message too long.";
        return "Message ready to send.";
    }

    /**
     * Validates the recipient cell number.
     * Must start with + and be no longer than 10 characters (Part 2 rule).
     */
    public boolean checkRecipientCell() {
        return recipient != null && recipient.startsWith("+") && recipient.length() <= 10;
    }

    // ---------- Hash ----------

    /**
     * Creates a message hash in format: ID[0:2]:messageNumber:FIRSTWORDLASTWORD
     */
    public String createMessageHash() {
        if (messageText == null || messageText.trim().isEmpty()) return "";
        String idPart = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].toUpperCase();
        String last = words[words.length - 1].toUpperCase();
        this.messageHash = idPart + ":" + messageNumber + ":" + first + last;
        return this.messageHash;
    }

    /**
     * Returns the stored message hash (generates it if not yet created).
     */
    public String getMessageHash() {
        if (messageHash == null) {
            createMessageHash();
        }
        return messageHash;
    }

    // ---------- Send / Store / Discard ----------

    /**
     * Handles the user's choice to send, discard, or store a message.
     * 1 = Send, 2 = Disregard, 3 = Store
     */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                totalMessagesSent++;
                return "Message successfully sent.";
            }
            case 2 -> {
                return "Message disregarded.";
            }
            case 3 -> {
                storeMessageToJSON();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid choice.";
            }
        }
    }

    /**
     * Returns a string showing total messages sent so far.
     */
    public static String returnTotalMessages() {
        return "Total messages sent: " + totalMessagesSent;
    }

    // ---------- JSON Storage ----------


    public void storeMessageToJSON() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.json", true))) {
            JSONObject obj = new JSONObject();
            obj.put("messageID", messageID);
            obj.put("messageHash", getMessageHash());
            obj.put("recipient", recipient);
            obj.put("messageText", messageText);
            writer.write(obj.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // ---------- Helper methods ----------

    public String getFirstWord() {
        if (messageText == null) return "";
        return messageText.trim().split("\\s+")[0].toUpperCase();
    }

    public String getLastWord() {
        if (messageText == null) return "";
        String[] words = messageText.trim().split("\\s+");
        return words[words.length - 1].toUpperCase();
    }
public static String displayLongestMessage() {
        String longest = "";
        for (String msg : storedMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }
    public static void loadStoredMessages() {
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);
                String text = obj.getString("messageText");
                storedMessages.add(text);
            }
        } catch (IOException e) {
            // No file yet - continue without crashing
        }
    }

 public static String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < recipientList.size(); i++) {
            if (recipientList.get(i).equals(recipient)) {
                results.append(sentMessages.get(i)).append("\n");
            }
        }
        if (results.length() == 0) return "No messages found for recipient.";
        return results.toString().trim();
    }
 public static String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deletedText = "";
                if (i < sentMessages.size()) {
                    deletedText = sentMessages.get(i);
                    sentMessages.remove(i);
                    if (i < recipientList.size()) recipientList.remove(i);
                }
                messageHashes.remove(i);
                messageIDs.remove(i);
                return "Message: " + deletedText + " successfully deleted.";
            }
        }
        return "Hash not found.";
    }
 public static String printMessages() {
        StringBuilder report = new StringBuilder();
        report.append("==== Message Report ====\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            report.append("-----------------------------\n");
            report.append("Hash      : ").append(i < messageHashes.size() ? messageHashes.get(i) : "N/A").append("\n");
            report.append("Recipient : ").append(i < recipientList.size() ? recipientList.get(i) : "N/A").append("\n");
            report.append("Message   : ").append(sentMessages.get(i)).append("\n");
        }
        report.append("-----------------------------");
        return report.toString();
    }
 public static String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                if (i < sentMessages.size()) {
                    return sentMessages.get(i);
                }
            }
        }
        return "Message not found.";
    }
 

    // ---------- Getters and Setters ----------

    public String getMessageID()                    { return messageID; }
    public String getRecipient()                    { return recipient; }
    public void setRecipient(String recipient)      { this.recipient = recipient; }
    public String getMessageText()                  { return messageText; }
    public void setMessageText(String messageText)  { this.messageText = messageText; }
    public int getMessageNumber()                   { return messageNumber; }
}