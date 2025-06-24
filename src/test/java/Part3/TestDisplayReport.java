package Part3;


import com.mycompany.assignment.Assignment.Message;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tevin
 */
public class TestDisplayReport {


    private List<Message> allMessages;

    @BeforeEach
    public void setup() {
        allMessages = new ArrayList<>();

        allMessages.add(new Message("+27834557896", "Did you get the cake?", 1)); // Sent
        allMessages.add(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2)); // Sent
        allMessages.add(new Message("+27838884567", "Yohooooo I am at the gate", 3)); // Disregarded (included for test purposes)
        allMessages.add(new Message("0838884567", "It is dinner time", 4)); // Sent, invalid format
        allMessages.add(new Message("+27838884567", "Ok, I am leaving without you.", 5)); // Stored
    }

    @Test
    public void testDisplayFullMessageReport() {
        StringBuilder report = new StringBuilder();

        for (Message msg : allMessages) {
            report.append(msg.toString()).append("\n\n");
        }

        String output = report.toString();

        // ✅ Validate the structure and a few key expected parts:
        assertTrue(output.contains("Message #1"));
        assertTrue(output.contains("Message #2"));
        assertTrue(output.contains("To: +27834557896"));
        assertTrue(output.contains("To: +27838884567"));
        assertTrue(output.contains("Did you get the cake?"));
        assertTrue(output.contains("Ok, I am leaving without you."));

        // Optional: check total number of reports
        long messageCount = output.lines().filter(line -> line.startsWith("Message #")).count();
        assertEquals(5, messageCount, "Expected 5 messages to be included in report.");
    }
}    

