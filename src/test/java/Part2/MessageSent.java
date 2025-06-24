package Part2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author tevin
 */
public class MessageSent {
static class Message {
        private final long messageID;
        private final int messageNumber;
        private final String recipient;
        private final String content;
        private boolean sent = false;
        private boolean stored = false;

        public Message(int messageNumber, String recipient, String content) {
            this.messageID = 1000000000L + (long)(Math.random() * 8999999999L);
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.content = content;
        }

        public String getStatus(String action) throws IOException {
            switch (action.toLowerCase()) {
                case "send" -> {
                    sent = true;
                    return "Message successfully sent";
                }
                case "discard" -> {
                    return "Press zero to delete message";
                }
                case "store" -> {
                    storeMessageAsJson();
                    stored = true;
                    return "Message successfully stored";
                }
                default -> {
                    return "Invalid option";
                }
            }
        }

        private void storeMessageAsJson() throws IOException {
            String json = String.format("{\"id\":%d,\"recipient\":\"%s\",\"content\":\"%s\"}",
                    messageID, recipient, content);
            try (FileWriter writer = new FileWriter("stored_message_" + messageNumber + ".json")) {
                writer.write(json);
            }
        }

        public boolean isSent() { return sent; }
        public boolean isStored() { return stored; }
    }

    @Test
    void testSendMessageSuccess() throws IOException {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String status = msg.getStatus("send");
        assertEquals("Message successfully sent", status);
        assertTrue(msg.isSent());
    }

    @Test
    void testDiscardMessage() throws IOException {
        Message msg = new Message(2, "08575975889", "Hi Keegan, did you receive payment");
        String status = msg.getStatus("discard");
        assertEquals("Press zero to delete message", status);
        assertFalse(msg.isSent());
        assertFalse(msg.isStored());
    }

    @Test
    void testStoreMessage() throws IOException {
        Message msg = new Message(3, "+27831234567", "Please call back");
        String status = msg.getStatus("store");

        assertEquals("Message successfully stored", status);
        assertTrue(msg.isStored());

        File file = new File("stored_message_3.json");
        assertTrue(file.exists());

        String content = Files.readString(file.toPath());
        assertTrue(content.contains("Please call back"));

        // Cleanup
        file.delete();
    }

    @Test
    void testInvalidOption() throws IOException {
        Message msg = new Message(4, "+27712345678", "Test message");
        String status = msg.getStatus("unknown");
        assertEquals("Invalid option", status);
    }
}
