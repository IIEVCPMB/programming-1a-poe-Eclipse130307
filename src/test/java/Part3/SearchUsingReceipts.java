package Part3;


import com.mycompany.assignment.Assignment.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class SearchUsingReceipts {

 private static List<Message> allMessages;

    @BeforeAll
    public static void setup() {
        allMessages = new ArrayList<>();

        // Sent messages (simulated)
        allMessages.add(new Message("+27834557896", "Did you get the cake?", 1));
        allMessages.add(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2));
        allMessages.add(new Message("+27838884567", "Yohooooo I am at the gate", 3));
        allMessages.add(new Message("0838884567", "It is dinner time", 4));  // invalid recipient

        // Stored messages (simulated)
        allMessages.add(new Message("+27838884567", "Ok, I am leaving without you.", 5));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        String targetRecipient = "+27838884567";

        List<Message> messagesForRecipient = allMessages.stream()
            .filter(msg -> msg.getRecipient().equals(targetRecipient))
            .collect(Collectors.toList());

        // Extract contents for easier assertion
        List<String> contents = messagesForRecipient.stream()
            .map(Message::getContent)
            .collect(Collectors.toList());

        // Check that both messages are found
        assertTrue(contents.contains("Where are you? You are late! I have asked you to be on time."),
                "Should contain the long late message");
        assertTrue(contents.contains("Ok, I am leaving without you."),
                "Should contain the stored message 'Ok, I am leaving without you.'");

        // Optionally, check that only those two messages match recipient
        assertEquals(3, messagesForRecipient.size(), "Expected 3 messages sent or stored to recipient +27838884567");
    }
}   

