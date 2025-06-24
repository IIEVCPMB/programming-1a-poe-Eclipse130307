package Part3;


import com.mycompany.assignment.Assignment.Message;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class TestDisplayLongestMessage {
  
private static List<Message> testSentMessages;

    @BeforeAll
    public static void setup() {
        testSentMessages = new ArrayList<>();

        testSentMessages.add(new Message("+27834557896", "Did you get the cake?", 1));
        testSentMessages.add(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2));
        testSentMessages.add(new Message("+27838884567", "Yohooooo I am at the gate", 3));
        testSentMessages.add(new Message("0838884567", "It is dinner time", 4)); // invalid
    }

    @Test
    public void testLongestMessageDetection() {
        Message longest = testSentMessages.stream()
            .max((a, b) -> Integer.compare(a.getContent().length(), b.getContent().length()))
            .orElse(null);

        assertNotNull(longest, "Should find a longest message");
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getContent());
        assertEquals(60, longest.getContent().length(), "Expected message length to be 60 characters");
    }
}    
    

