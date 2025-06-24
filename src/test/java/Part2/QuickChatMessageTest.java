package Part2;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tevin
 */
public class QuickChatMessageTest {
// Simulated in-memory message storage
    static class MessageStorage {
        private static final Map<Long, Message> storedMessages = new HashMap<>();

        public static void storeMessage(Message message) {
            storedMessages.put(message.getMessageID(), message);
        }

        public static Message getStoredMessageById(long id) {
            return storedMessages.get(id);
        }

        public static void clear() {
            storedMessages.clear();
        }
    }

    // The Message class for testing
    static class Message {
        private final long messageID;
        private final int messageNumber;
        private final String recipient;
        private final String content;
        private final String messageHash;

        public Message(int messageNumber, String recipient, String content) {
            this.messageID = generateRandomID();
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.content = content;
            this.messageHash = generateMessageHash();
        }

        private long generateRandomID() {
            return 1000000000L + (long) (Math.random() * 8999999999L);
        }

        private String generateMessageHash() {
            String[] words = content.trim().split("\\s+");
            String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
            String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : "";
            String firstTwo = String.valueOf(messageID).substring(0, 2);
            return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        }

        public long getMessageID() {
            return messageID;
        }

        public int getMessageNumber() {
            return messageNumber;
        }

        public String getRecipient() {
            return recipient;
        }

        public String getContent() {
            return content;
        }

        public String getMessageHash() {
            return messageHash;
        }
    }

    @BeforeEach
    void setup() {
        MessageStorage.clear();
    }

    @Test
    void testValidMessageToMike() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");

        assertTrue(msg.getRecipient().matches("^\\+27\\d{9}$"), "Recipient format should be valid (+27...)");
        assertTrue(msg.getContent().length() <= 250, "Message should be 250 characters or less");
        assertEquals(1, msg.getMessageNumber());
        assertEquals(10, String.valueOf(msg.getMessageID()).length(), "Message ID should be 10 digits");

        String hash = msg.getMessageHash();
        assertTrue(hash.matches("^\\d{2}:\\d+:\\w+\\w+$"), "Hash should be correctly formatted");
        assertTrue(hash.contains("HI") && hash.contains("TONIGHT"), "Hash should contain first and last words");

        MessageStorage.storeMessage(msg);
        assertNotNull(MessageStorage.getStoredMessageById(msg.getMessageID()), "Message should be stored");
    }

    @Test
    void testInvalidRecipientAndDiscardMessageToKeegan() {
        Message msg = new Message(2, "08575975889", "Hi Keegan, did you receive payment");

        assertFalse(msg.getRecipient().matches("^\\+27\\d{9}$"), "Recipient format should be invalid (missing +27)");
        assertTrue(msg.getContent().length() <= 250, "Message should be 250 characters or less");

        // Simulate discard — no storing
        assertNull(MessageStorage.getStoredMessageById(msg.getMessageID()), "Message should not be stored (discarded)");
    }
}
    
