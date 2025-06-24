package Part3;


import com.mycompany.assignment.Assignment.Message;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class DeleteUsingHash {
private List<Message> sentMessages;
    private Message messageToDelete;

    @BeforeEach
    public void setup() {
        sentMessages = new ArrayList<>();

        sentMessages.add(new Message("+27834557896", "Did you get the cake?", 1));

        messageToDelete = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.", 2);
        sentMessages.add(messageToDelete);

        sentMessages.add(new Message("+27838884567", "Yohooooo I am at the gate", 3));
        sentMessages.add(new Message("+27838884567", "Ok, I am leaving without you.", 4));
    }

    @Test
    public void testDeleteMessageByHashWithConfirmationMessage() {
        String hashToDelete = messageToDelete.getMessageHash();

        Message found = null;
        for (Message msg : sentMessages) {
            if (msg.getMessageHash().equals(hashToDelete)) {
                found = msg;
                break;
            }
        }

        assertNotNull(found, "Message should be found before deletion.");

        // Simulate user confirming deletion
        sentMessages.remove(found);

        // Simulate return confirmation message
        String confirmation = "Message \"" + found.getContent() + "\" successfully deleted.";

        // Assertions
        assertFalse(sentMessages.contains(found), "Message should be removed from the list.");
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                     confirmation, "Correct confirmation message should be generated.");
    }
}    

