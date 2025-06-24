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
public class SearchMessageID {

    private static Message message4;
    private static List<Message> testSentMessages;
    
    @BeforeAll
    public static void setup() {
        testSentMessages = new ArrayList<>();

        testSentMessages.add(new Message("+27834557896", "Did you get the cake?", 1));
        testSentMessages.add(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2));
        testSentMessages.add(new Message("+27838884567", "Yohooooo I am at the gate", 3));
        message4 = new Message("0838884567", "It is dinner time", 4);
        testSentMessages.add(message4);
    }

    @Test
    public void testSearchByMessageId() {
        String searchId = message4.getMessageId(); // capture the actual ID

        Message found = testSentMessages.stream()
            .filter(m -> m.getMessageId().equals(searchId))
            .findFirst()
            .orElse(null);

        assertNotNull(found, "Message should be found by ID");
        assertEquals("0838884567", found.getRecipient(), "Recipient must match");
        assertEquals("It is dinner time", found.getContent(), "Message content must match");
        assertEquals(searchId, found.getMessageId(), "Message ID must match");
    }
}

    

