/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tevin
 */
    
public class Assignment {

public static class Message {
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
    private static final ArrayList<Message> sentMessages = new ArrayList<>();

    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*()_+=<>?/].*");
    }

    public static boolean checkCellPhoneNumber(String cell) {
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
        if (!checkUserName(username)) {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Username successfully captured");
        }

        String password = JOptionPane.showInputDialog("Enter password");
        if (!checkPasswordComplexity(password)) {
            JOptionPane.showMessageDialog(null, "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Password successfully captured");
        }

        String cellphone = JOptionPane.showInputDialog("Enter cellphone number");
        if (!checkCellPhoneNumber(cellphone)) {
            JOptionPane.showMessageDialog(null, "Cellphone number incorrectly formatted or does not contain international code, please check the number and try again.");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Cell phone number successfully added");
        }

        registeredUsername = username;
        registeredPassword = password;
        registeredCell = cellphone;

        JOptionPane.showMessageDialog(null, "Registration Successful");
    }

    public static void login() throws IOException {
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

    public static void showChatMenu() throws IOException {
        while (true) {
            String choice = JOptionPane.showInputDialog(
                "1 - Send Message\n" +
                "2 - Show Stored Messages\n" +
                "3 - Tracking and Reporting\n" +
                "4 - Quit");

            if (choice == null) continue;
            switch (choice) {
                case "1" -> {
                    if (messageCounter > maxMessages) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                    } else {
                        createMessage();
                    }
                }
                case "2" -> showStoredMessages();
                case "3" -> trackingAndReporting();
                case "4" -> { return; }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

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
                sentMessages.add(msg);
                messageCounter++;
            }
            case 2 -> {
                storeMessageAsJson(msg);
                sentMessages.add(msg);
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

    public static void showStoredMessages() throws IOException {
        File dir = new File(".");
        File[] messageFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("stored_message_") && name.endsWith(".json");
            }
        });

        if (messageFiles == null || messageFiles.length == 0) {
            JOptionPane.showMessageDialog(null, "No stored messages found.");
            return;
        }

        StringBuilder allMessages = new StringBuilder();
        for (File file : messageFiles) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getName())));
                JSONObject obj = new JSONObject(content);
                allMessages.append("Message #").append(obj.getInt("message_number"))
                    .append("\nTo: ").append(obj.getString("recipient"))
                    .append("\nContent: ").append(obj.getString("content"))
                    .append("\nHash: ").append(obj.getString("hash"))
                    .append("\n\n");
            } catch (JSONException e) {
                allMessages.append("Failed to read: ").append(file.getName()).append("\n\n");
            }
        }

        JOptionPane.showMessageDialog(null, allMessages.toString(), "Stored Messages", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void trackingAndReporting() {
        while (true) {
            String option = JOptionPane.showInputDialog(
                "Tracking & Reporting:\n" +
                "1 - Show Sender and Recipient of All Sent Messages\n" +
                "2 - Show Longest Sent Message\n" +
                "3 - Search by Message ID\n" +
                "4 - Search Messages by Recipient\n" +
                "5 - Delete Message by Hash\n" +
                "6 - Show Full Message Report\n" +
                "7 - Back");

            switch (option) {
                case "1" -> {
                    StringBuilder sb = new StringBuilder();
                    for (Message msg : sentMessages) {
                        sb.append("From: ").append(registeredCell)
                          .append(" | To: ").append(msg.getRecipient()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No messages sent.");
                }
                case "2" -> {
                    Message longest = null;
                    for (Message msg : sentMessages) {
                        if (longest == null || msg.getContent().length() > longest.getContent().length()) {
                            longest = msg;
                        }
                    }
                    JOptionPane.showMessageDialog(null, longest != null ? longest.toString() : "No messages found.");
                }
                case "3" -> {
                    String id = JOptionPane.showInputDialog("Enter Message ID:");
                    Message found = null;
                    for (Message msg : sentMessages) {
                        if (msg.getMessageId().equals(id)) {
                            found = msg;
                            break;
                        }
                    }
                    if (found != null) {
                        JOptionPane.showMessageDialog(null, "To: " + found.getRecipient() + "\nContent: " + found.getContent());
                    } else {
                        JOptionPane.showMessageDialog(null, "Message not found.");
                    }
                }
                case "4" -> {
                    String target = JOptionPane.showInputDialog("Enter recipient number (+27...):");
                    StringBuilder sb = new StringBuilder();
                    for (Message msg : sentMessages) {
                        if (msg.getRecipient().equals(target)) {
                            sb.append(msg.toString()).append("\n\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No messages to this recipient.");
                }
                case "5" -> {
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");

                    Message toDelete = null;
                    for (Message msg : sentMessages) {
                    if (msg.getMessageHash().equals(hash)) {
                    toDelete = msg;
                    break;
                        }
                    }

                    if (toDelete != null) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                   "Are you sure you want to delete this message?\n\n" + toDelete.toString(),
                   "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            sentMessages.remove(toDelete);
            JOptionPane.showMessageDialog(null,
                "Message \"" + toDelete.getContent() + "\" successfully deleted.");}     
                } else {
            JOptionPane.showMessageDialog(null, "Message not found.");
                         }
                     }

                case "6" -> {
                    StringBuilder sb = new StringBuilder();
                    for (Message msg : sentMessages) {
                        sb.append(msg.toString()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No messages to report.");
                }
                case "7" -> { return; }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        while (true) {
            String[] options = {"Register", "Login"};
            int choice = JOptionPane.showOptionDialog(null, "Select an option", ":",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

            if (choice == 0) {
                registration();
            } else {
                login();
                break;
            }
        }
    }
}