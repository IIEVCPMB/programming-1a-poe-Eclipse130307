/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author tevin
 */

public class Assignment {
    static class Message {
        private final String messageId;
        private int messageNumber;
        private String recipient;
        private String content;
        private String messageHash;

        public Message(String recipient, String content, int messageNumber) {
            this.messageId = generateMessageId();
            this.recipient = recipient;
            this.content = content;
            this.messageNumber = messageNumber;
            this.messageHash = generateHash();
        }

        private String generateMessageId() {
            Random rand = new Random();
            return String.format("%010d", rand.nextInt(1_000_000_000));
        }

        private String generateHash() {
            String[] words = content.trim().split("\\s+");
            String firstWord = words.length > 0 ? words[0].toUpperCase() : "NONE";
            String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;
            return messageId.substring(0, 2) + ":" + (messageNumber - 1) + ":" + firstWord + lastWord;
        }

        public boolean isValidRecipient() {
            return recipient != null && recipient.matches("^\\+27\\d{9}$");
        }

        public boolean isValidContent() {
            return content != null && content.length() <= 250;
        }

        public JSONObject toJSON() {
            JSONObject obj = new JSONObject();
            obj.put("message_id", messageId);
            obj.put("message_number", messageNumber);
            obj.put("recipient", recipient);
            obj.put("content", content);
            obj.put("hash", messageHash);
            return obj;
        }

        public String getMessageId() { return messageId; }
        public int getMessageNumber() { return messageNumber; }
        public String getRecipient() { return recipient; }
        public String getContent() { return content; }
        public String getMessageHash() { return messageHash; }

        @Override
        public String toString() {
            return "Message #" + messageNumber +
                    "\nTo: " + recipient +
                    "\nHash: " + messageHash +
                    "\nContent: " + content;
        }
    }

    private static String registeredUsername;
    private static String registeredPassword;
    private static String registeredCell;
    private static boolean isLoggedIn = false;
    private static int maxMessages = 0;
    private static int messageCounter = 1;
    private static final String PROFILE_FILE = "user_profile.json";
    
    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
        
    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&             // Capital letter
               password.matches(".*\\d.*") &&               // Number
               password.matches(".*[!@#$%^&*()_+=<>?/].*"); // Special character
    }
     // When prompted with "Create a regular expression based cell phone checker that ensures the cell phone number contains the international country code followed by the number which is no longer than 10 characters long.", Chat GPT then produced the following code.
     // Open AI.(2025).ChatGPT(April 18 version)[large language model]
     // https://chat.openai.com/chat
    
     public static boolean checkCellPhoneNumber(String cell) {
        // +27 followed by 9 digits = total 12 characters
        return cell.matches("^\\+27\\d{9}$");
    }
    
    public static void saveProfile() {
        JSONObject obj = new JSONObject();
        obj.put("username", registeredUsername);
        obj.put("password", registeredPassword);
        obj.put("cell", registeredCell);

        try (FileWriter file = new FileWriter(PROFILE_FILE)) {
            file.write(obj.toString(4));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save profile.");
        }
    }

    public static boolean loadProfile() {
        File file = new File(PROFILE_FILE);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            JSONObject obj = new JSONObject(json.toString());
            registeredUsername = obj.getString("username");
            registeredPassword = obj.getString("password");
            registeredCell = obj.getString("cell");

            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load profile.");
            return false;
        }
    }

    public static void registration() {
        
    if (new File(PROFILE_FILE).exists()) {
            JOptionPane.showMessageDialog(null, "User already registered. Please log in.");
            return;
    }
     
    String username = JOptionPane.showInputDialog("Enter username");
        if (!checkUserName(username)) 
        {JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in lenght"); return;}
         else
        {JOptionPane.showMessageDialog(null, "Username successfully captured");}
        
    String password = JOptionPane.showInputDialog("Enter password");
        if (!checkPasswordComplexity(password)) 
        {JOptionPane.showMessageDialog(null, "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");return;}
         else
        {JOptionPane.showMessageDialog(null, "Password successfully captured");}
    
    String cellphone = JOptionPane.showInputDialog("Enter cellphone number");
        if (!checkCellPhoneNumber(cellphone)) 
        {JOptionPane.showMessageDialog(null, "Cellphone number incorrectly formatted or does not contain international code, please the number and try again.");return;}
         else
        {JOptionPane.showMessageDialog(null, "Cell phone number successfully added");}
    
    registeredUsername = username;
    registeredPassword = password;
    registeredCell = cellphone;
    
       JOptionPane.showMessageDialog(null, "Registration Successful");
    }
    
    public static void login() {
     if (registeredUsername == null) {
            JOptionPane.showMessageDialog(null, "No user registered.");
            return;
        }

        String username = JOptionPane.showInputDialog("Login username:");
        String password = JOptionPane.showInputDialog("Login password:");

        if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
            isLoggedIn = true;
            JOptionPane.showMessageDialog(null, "Welcome to Quickchat!");
            String input = JOptionPane.showInputDialog("How many messages do you want to send?");
            if (input != null && input.matches("\\d+")) {
                maxMessages = Integer.parseInt(input);
                showChatMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid number.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login failed.");
      }
    }

    // === Chat Menu ===
    public static void showChatMenu() {
        while (true) {
            String choice = JOptionPane.showInputDialog("1 - Send Message\n2 - Show Messages (coming soon)\n3 - Quit");

            if (choice == null) continue;

            switch (choice) {
                case "1" -> {
                    if (messageCounter > maxMessages) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                    } else {
                        createMessage();
                    }
                }
                case "2" -> JOptionPane.showMessageDialog(null, "Coming soon.");
                case "3" -> {
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    // === Message Handling ===
    public static void createMessage() {
        String recipient = JOptionPane.showInputDialog("Enter recipient cell (+27 and 9 digits):");
        String content = JOptionPane.showInputDialog("Enter message (≤ 250 characters):");

        Message msg = new Message(recipient, content, messageCounter);

        if (!msg.isValidRecipient()) {
            JOptionPane.showMessageDialog(null, "Invalid recipient number.");
            return;
        }

        if (!msg.isValidContent()) {
            JOptionPane.showMessageDialog(null, "Please enter a message less than 250 characters.");
            return;
        }

        String[] options = {"Send Message", "Disregard Message", "Store to Send Later"};
        int option = JOptionPane.showOptionDialog(null,
                "Choose what to do with the message:\n" + msg.toString(),
                "Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        switch (option) {
            case 0 -> {
                JOptionPane.showMessageDialog(null, "Message sent.");
                messageCounter++;
            }
            case 2 -> {
                storeMessageAsJson(msg);
                messageCounter++;
            }
            default -> JOptionPane.showMessageDialog(null, "Message discarded.");
        }
    }

    public static void storeMessageAsJson(Message msg) {
        try (FileWriter file = new FileWriter("stored_message_" + msg.getMessageId() + ".json")) {
            file.write(msg.toJSON().toString(4));
            JOptionPane.showMessageDialog(null, "Message stored for later.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving message.");
        }
    }
    
    public static void main(String[] args) {

    while (true){
    String[] options = {"Register","Login"};
    int choice = JOptionPane.showOptionDialog(null,"Select an option",":",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.INFORMATION_MESSAGE,
    null, options, options[0]);
    
    if (choice == 0)
    {registration();}
    else
    {login() ;break;}
        }
    }
}