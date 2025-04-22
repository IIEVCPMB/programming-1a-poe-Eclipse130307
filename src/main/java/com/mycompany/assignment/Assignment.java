/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment;

import javax.swing.JOptionPane;

/**
 *
 * @author tevin
 */
public class Assignment {
    
    private static String registeredUsername;
    private static String registeredPassword;
    private static String registeredCell;
   
    //Used Java Boolean tutorial on Youtube
    //https://www.youtube.com/watch?v=XjJz1H0Bqlw&t=155s
    
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
    
    public static void main(String[] args) {
    
    //Used same choice method from StudentDiscount activity. And further explanation from youtube.
    //https://www.youtube.com/watch?v=acx5WaKRPWU&t=60s

    
    while (true){
    String[] options = {"Register","Login"};
    int choice = JOptionPane.showOptionDialog(null,"Select an option",":",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.INFORMATION_MESSAGE,
    null, options, options[0]);
    
    if (choice == 0)
    {registration();}
    else
    {login();}
     }
    }  
    
    public static void registration() {
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
            
    if (registeredUsername == null){
    JOptionPane.showMessageDialog(null, "No user registered yet. Please register first.");return;}

    String username = JOptionPane.showInputDialog("Login - Enter username:");
    if (username == null) return;

    String password = JOptionPane.showInputDialog("Enter password:");
    if (password == null) return;

    if (username.equals(registeredUsername) && password.equals(registeredPassword)) 
    {JOptionPane.showMessageDialog(null,"Welcome" +username+ " It is great to see you again");} 
    else 
    {JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again");}
    
    }
}