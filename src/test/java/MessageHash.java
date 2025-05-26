/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tevin
 */
public class MessageHash {

static class Message {
        private final String messageIDPrefix;
        private final int messageNumber;
        private final String content;
        private final String messageHash;

        public Message(String idPrefix, int messageNumber, String content) {
            this.messageIDPrefix = idPrefix;
            this.messageNumber = messageNumber;
            this.content = content;
            this.messageHash = generateMessageHash();
        }

        private String generateMessageHash() {
            String[] words = content.trim().split("\\s+");
            String first = words.length > 0 ? words[0].toUpperCase() : "";
            String last = words.length > 1 ? words[words.length - 1].toUpperCase() : first;
            return messageIDPrefix + ":" + messageNumber + ":" + first + last;
        }

        public String getMessageHash() {
            return messageHash;
        }
    }

    @Test
    void testExpectedHashFromCase1() {
        String expectedHash = "00:0:HITONIGHT";
        Message msg = new Message("00", 0, "Hi Mike, can you join us for dinner tonight");
        assertEquals(expectedHash, msg.getMessageHash(), "Hash should be 00:0:HITONIGHT");
    }
}
