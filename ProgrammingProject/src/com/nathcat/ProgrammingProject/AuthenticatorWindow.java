package com.nathcat.ProgrammingProject;

// Import the required libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AuthenticatorWindow {

    // Declare public variable allowAccess, this will be used by the main method to determine when to continue
    public static boolean allowAccess = false;

    private static JTextArea p1_username_box;
    private static JTextArea p1_password_box;
    private static JTextArea p2_username_box;
    private static JTextArea p2_password_box;

    public static JFrame root;

    private static final ArrayList<String> correct_usernames = new ArrayList<String>();
    private static final ArrayList<String> correct_passwords = new ArrayList<String>();

    public static void GUI() {
        // Populate the username and password lists
        correct_usernames.add("Nathcat");
        correct_usernames.add("Herman");
        correct_passwords.add("Nathcat123");
        correct_passwords.add("Herman123");

        // Initiate the GUI and add a title
        root = new JFrame();
        root.setTitle("Please enter your login details");
        // Set the default exit operation so that the program exits if the window is closed
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the window
        root.setSize(400, 200);

        // Create the entry fields for the players to enter their login details
        p1_username_box = new JTextArea();
        p1_username_box.setText("Enter player one's username");
        p1_password_box = new JTextArea();
        p1_password_box.setText("Enter player one's password");

        p2_username_box = new JTextArea();
        p2_username_box.setText("Enter player two's username");
        p2_password_box = new JTextArea();
        p2_password_box.setText("Enter player two's password");

        // Add a button to submit their login details
        JButton login = new JButton("Login");
        // Add an ActionListener to the button, so that it validates your details when clicked.
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateDetails();
            }
        });

        // Create a JPanel to hold the button
        JPanel panel = new JPanel();
        panel.setSize(400, 100);

        // Create a JPanel to hold the entry boxes
        JPanel entryPanel = new JPanel();
        entryPanel.setSize(400, 300);
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.PAGE_AXIS));

        // Set the size of the components
        p1_username_box.setSize(400, 50);
        p1_password_box.setSize(400, 50);

        p2_username_box.setSize(400, 50);
        p2_password_box.setSize(400, 50);

        // Add the entry components to entryPanel, space them apart by 10 pixels
        entryPanel.add(p1_username_box);
        entryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        entryPanel.add(p1_password_box);
        entryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        entryPanel.add(p2_username_box);
        entryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        entryPanel.add(p2_password_box);

        // Add the components to root
        // Add the login button to the panel
        panel.add(login);

        // Add the panel to root
        root.getContentPane().add(panel, BorderLayout.SOUTH);
        // Add the entry panel to root
        root.getContentPane().add(entryPanel, BorderLayout.NORTH);

        // Make the window visible
        root.setVisible(true);
    }

    public static void validateDetails() {
        boolean allow_access = true;

        // Get the values in the entry boxes and assign them to variables
        String p1_username = p1_username_box.getText();
        String p1_password = p1_password_box.getText();
        String p2_username = p2_username_box.getText();
        String p2_password = p2_password_box.getText();

        // Check if the values are incorrect, if so, set allow_access to false
        if (!correct_usernames.contains(p1_username)) {
            allow_access = false;
        }
        if (!correct_usernames.contains(p2_username)) {
            allow_access = false;
        }
        if (!correct_passwords.contains(p1_password)) {
            allow_access = false;
        }
        if (!correct_passwords.contains((p2_password))) {
            allow_access = false;
        }

        allowAccess = allow_access;
        System.out.println(allow_access);
        System.out.println(allowAccess);
    }
}
