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

public class RecipientNumber {
    // Validator method using regex
    public static boolean isValidRecipient(String number) {
        return number.matches("^\\+27\\d{9}$");
    }
    
    @Test
    void testValidRecipientSuccess() {
        String validRecipient = "+27718693002";
        assertTrue(isValidRecipient(validRecipient), "Cell phone number successfully captured");
    }

    @Test
    void testInvalidRecipientTooShort() {
        String shortRecipient = "+27123456";  // too few digits
        assertFalse(isValidRecipient(shortRecipient), "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    void testInvalidRecipientWrongPrefix() {
        String wrongPrefix = "+44718693002";  // not South Africa
        assertFalse(isValidRecipient(wrongPrefix), "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    void testInvalidRecipientTooLong() {
        String longRecipient = "+277186930020";  // too many digits
        assertFalse(isValidRecipient(longRecipient), "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    void testInvalidRecipientNoPlus() {
        String missingPlus = "27718693002";  // missing +
        assertFalse(isValidRecipient(missingPlus), "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }
}   

    
