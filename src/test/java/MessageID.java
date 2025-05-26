/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tevin
 */
public class MessageID {
// Inner Message class for testing purposes
    static class Message {
        private final long messageID;
        private final int messageNumber;
        private final String recipient;
        private final String content;

        public Message(int messageNumber, String recipient, String content) {
            // Generate a 10-digit random message ID
            this.messageID = 1000000000L + (long) (Math.random() * 8999999999L);
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.content = content;

            // Print the Message ID as required
            System.out.println("MessageID generated : " + messageID);
        }

        public long getMessageID() {
            return messageID;
        }
    }

    @Test
    void testMessageIDLengthAndFormat() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");

        long id = msg.getMessageID();
        String idStr = String.valueOf(id);

        // Check that the ID has exactly 10 digits
        assertEquals(10, idStr.length(), "Message ID should be 10 digits long");

        // Optionally check that it's within the valid range (just a safety check)
        assertTrue(id >= 1000000000L && id <= 9999999999L, "Message ID should be a valid 10-digit number");
    }
}

