package Part3;


import com.mycompany.assignment.Assignment.Message;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tevin
 */
public class SentMessageArray {

    private static List<Message> testSentMessages;

    @BeforeAll
    public static void setup() {
        testSentMessages = new ArrayList<>();

        // Developer-entered test data messages
        testSentMessages.add(new Message("+27834557896", "Did you get the cake?", 1));       // Sent
        testSentMessages.add(new Message("+27838884567", "Yohoooo, I am at your gate", 2));  // Disregarded (still added for test)
        testSentMessages.add(new Message("+27838884567", "It is dinner time!", 3));          // Sent
        testSentMessages.add(new Message("0838884567", "It is dinner time", 4));             // Invalid recipient
    }

    @Test
    public void testMessagesCount() {
        assertEquals(4, testSentMessages.size(), "There should be 4 messages total in test set");
    }

    @Test
    public void testSpecificMessagesExist() {
        boolean hasCakeMsg = testSentMessages.stream().anyMatch(m -> m.getContent().equals("Did you get the cake?"));
        boolean hasDinnerMsg = testSentMessages.stream().anyMatch(m -> m.getContent().equals("It is dinner time!"));

        assertTrue(hasCakeMsg, "'Did you get the cake?' should be in sent messages");
        assertTrue(hasDinnerMsg, "'It is dinner time!' should be in sent messages");
    }

    @Test
    public void testInvalidRecipientIsDetected() {
        Message msg4 = testSentMessages.get(3);
        assertFalse(msg4.isValidRecipient(), "Message 4 should have an invalid recipient");
    }
}

