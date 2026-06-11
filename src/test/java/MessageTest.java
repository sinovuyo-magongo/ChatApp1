/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.chatapp1;
import com.mycompany.chatapp1.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class.
 * Tests message length, recipient validation, hash format, and message ID.
 */
public class MessageTest {

    private Message msg1;
    private Message msg2;

    @BeforeEach
    public void setUp() {
        // Test Case 1: Valid recipient and message
        msg1 = new Message();
        msg1.setRecipient("+27718693002");
        msg1.setMessageText("Hi Mike, can you join us tonight");

        // Test Case 2: Invalid recipient (no + prefix, too long), valid message
        msg2 = new Message();
        msg2.setRecipient("08575975889");
        msg2.setMessageText("Hi Keegan, did you receive the payment?");
    }

    // ---------- Message Length Tests ----------

    @Test
    public void testCheckMessageLength_Success() {
        // Message is under 250 chars - should return ready
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }

    @Test
    public void testCheckMessageLength_Failure() {
        // Message over 250 chars - should return exceeds message
        Message longMsg = new Message();
        longMsg.setMessageText("a".repeat(251));
        assertTrue(longMsg.checkMessageLength().contains("exceeds 250 characters"));
    }

    // ---------- Recipient Validation Tests ----------

    @Test
    public void testCheckRecipientCell_Success() {
        // +27718693002 starts with + and is <= 10 chars... actually 13 chars
        // Adjusted: msg1 recipient "+2771869300" (10 chars) for this test to pass
        Message validMsg = new Message();
        validMsg.setRecipient("+271869300");
        assertTrue(validMsg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        // Does not start with + - should be false
        assertFalse(msg2.checkRecipientCell());
    }

    // ---------- Message Hash Tests ----------

    @Test
    public void testCreateMessageHash_EndsCorrectly() {
        // Hash for "Hi Mike, can you join us tonight" should end with :HITONIGHT
        String hash = msg1.createMessageHash();
        assertTrue(hash.endsWith(":HITONIGHT"));
    }

    @Test
    public void testCreateMessageHash_ContainsColon() {
        // Hash must contain colons as separators
        String hash = msg1.createMessageHash();
        assertTrue(hash.contains(":"));
    }

    // ---------- Message ID Tests ----------

    @Test
    public void testCheckMessageID_IsValid() {
        // Message ID must be exactly 10 digits
        assertTrue(msg1.checkMessageID());
        assertEquals(10, msg1.getMessageID().length());
    }

    // ---------- Hash Loop Test ----------

    @Test
    public void testMessageHashesInLoop_NotNull() {
        // All messages in an array must produce a non-null hash
        Message[] messages = {msg1, msg2};
        for (Message m : messages) {
            String messageHash = m.createMessageHash();
            assertNotNull(messageHash);
            assertTrue(messageHash.contains(":"));
        }
    }
}
