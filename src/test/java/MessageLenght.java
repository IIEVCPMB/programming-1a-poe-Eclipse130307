/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tevin
 */
public class MessageLenght {

static class Message {
        private final long messageID;
        private final int messageNumber;
        private final String recipient;
        private final String content;
        private final String messageHash;

        public Message(int messageNumber, String recipient, String content) {
            this.messageID = 1000000000L + (long) (Math.random() * 8999999999L);
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.content = content;
            this.messageHash = generateMessageHash();
        }

        private String generateMessageHash() {
            String[] words = content.trim().split("\\s+");
            String first = words.length > 0 ? words[0].toUpperCase() : "";
            String last = words.length > 1 ? words[words.length - 1].toUpperCase() : first;
            return String.valueOf(messageID).substring(0, 2) + ":" + messageNumber + ":" + first + last;
        }

        public long getMessageID() { return messageID; }
        public int getMessageNumber() { return messageNumber; }
        public String getRecipient() { return recipient; }
        public String getContent() { return content; }
        public String getMessageHash() { return messageHash; }
    }

    static class MessageHandler {
        private static final Map<Long, Message> sentMessages = new HashMap<>();

        public static boolean sendMessage(Message msg) {
            if (msg.getContent().length() > 250) {
                return false; // Message too long
            }
            sentMessages.put(msg.getMessageID(), msg);
            return true;
        }

        public static boolean isMessageSent(long id) {
            return sentMessages.containsKey(id);
        }

        public static void clear() {
            sentMessages.clear();
        }
    }

    @BeforeEach
    void setUp() {
        MessageHandler.clear();
    }

    @Test
    void testSendValidMessageSuccess() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us tonight?");
        boolean result = MessageHandler.sendMessage(msg);

        assertTrue(result, "Message ready to send");
        assertTrue(MessageHandler.isMessageSent(msg.getMessageID()), "Message should be stored");
    }

    @Test
    void testSendInvalidMessageTooLongFailure() {
        String longContent = "A".repeat(251);
        Message msg = new Message(2, "+27718693002", longContent);
        boolean result = MessageHandler.sendMessage(msg);

        assertFalse(result, "Message exceeds 250 characters by X, please reduce size");
        assertFalse(MessageHandler.isMessageSent(msg.getMessageID()), "Invalid message should not be stored");
    }
}
