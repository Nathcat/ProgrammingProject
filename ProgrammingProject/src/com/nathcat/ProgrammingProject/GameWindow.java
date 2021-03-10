package com.nathcat.ProgrammingProject;

// Import the required libraries
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


public class GameWindow {

    // Create a public variable that will store the window object
    public static JFrame root;

    public static JLabel playerOneCardImage = null;
    public static JLabel playerTwoCardImage = null;

    public static JPanel panel;

    public static JButton deckButton;

    public static void GUI() throws IOException {
        // Create the window by creating a new instance of JFrame
        root = new JFrame();
        // Set the title of the window
        root.setTitle("Card Game");
        // Set the close operation of the window so that it closes once the window is closed
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the window
        root.setSize(new Dimension(834, 565));


        // Read the card image via an input stream
        InputStream is = new BufferedInputStream(
                new FileInputStream("back_of_card.png")
        );

        Image cardImage = ImageIO.read(is);

        // Create a new button, with the card as its icon
        deckButton = new JButton(new ImageIcon(cardImage));
        deckButton.setSize(new Dimension(186, 259));

        // Remove the border of the button
        deckButton.setBorder(BorderFactory.createEmptyBorder());

        // ... and the contents
        deckButton.setContentAreaFilled(false);

        // Add an action listener to the button
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the index for the end of the deck (ie the top card's index)
                int endOfDeckIndex = Main.cardDeck.size() - 1;
                // If its player one's turn (isPlayerOneTurn = true), then add the card to their deck
                if (Main.isPlayerOneTurn) {
                    // Add the card to player one's deck
                    Main.playerOneDeck.add(Main.cardDeck.get(endOfDeckIndex));
                    // Make it player two's turn, (isPlayerOneTurn = false)
                    Main.isPlayerOneTurn = false;
                    // Display the image of the card
                    try {
                        displayPlayerOneCard(Main.cardDeck.get(endOfDeckIndex));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                } else {
                    // Add the card to player two's deck
                    Main.playerTwoDeck.add(Main.cardDeck.get(endOfDeckIndex));
                    // Make it player one's turn, (isPlayerOneTurn = true)
                    Main.isPlayerOneTurn = true;
                    // Display the image of the card
                    try {
                        displayPlayerTwoCard(Main.cardDeck.get(endOfDeckIndex));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    // Find the round winner
                    Main.findRoundWinner();
                }

                // Remove the top (end) card from the deck
                Main.cardDeck.remove(endOfDeckIndex);
            }
        });

        // Create a JPanel to hold the components
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Set the colour of the panel
        panel.setBackground(Color.green);
        // Add the components to the window
        panel.add(deckButton, BorderLayout.CENTER);

        // Add the panel to root
        root.getContentPane().add(panel, BorderLayout.CENTER);

        // Display the window
        root.setVisible(true);
    }

    public static void displayPlayerOneCard(String cardName) throws IOException {
        // Get the filepath of the card's image
        String filePath = "images/" + cardName + ".png";

        // Read the image
        BufferedImage cardImage = ImageIO.read(new File(filePath));

        // Create a new JLabel to display the card
        playerOneCardImage = new JLabel(new ImageIcon(cardImage));
        // Set the size of the JLabel
        playerOneCardImage.setSize(new Dimension(186, 259));

        // Destroy all components
        panel.removeAll();

        // Add the deck button to the panel
        panel.add(deckButton, BorderLayout.CENTER);

        // Add the card images if they are not equal to null
        if (playerOneCardImage != null) {
            panel.add(playerOneCardImage, BorderLayout.WEST);
        }

        if (playerTwoCardImage != null) {
            panel.add(playerTwoCardImage, BorderLayout.EAST);
        }

        // Add the panel to root
        root.getContentPane().add(panel);

        // Refresh the window
        root.revalidate();
        root.repaint();
    }

    public static void displayPlayerTwoCard(String cardName) throws IOException {
        // Get the filepath of the card's image
        String filePath = "images/" + cardName + ".png";

        // Read the image
        BufferedImage cardImage = ImageIO.read(new File(filePath));

        // Create a new JLabel to display the card
        playerTwoCardImage = new JLabel(new ImageIcon(cardImage));
        // Set the size of the JLabel
        playerTwoCardImage.setSize(new Dimension(186, 259));

        // Destroy all components
        panel.removeAll();

        // Add the deck button to the panel
        panel.add(deckButton, BorderLayout.CENTER);

        // Add the card images if they are not equal to null
        if (playerOneCardImage != null) {
            panel.add(playerOneCardImage, BorderLayout.WEST);
        }

        if (playerTwoCardImage != null) {
            panel.add(playerTwoCardImage, BorderLayout.EAST);
        }

        // Add the panel to root
        root.getContentPane().add(panel);

        // Refresh the window
        root.revalidate();
        root.repaint();
    }

    public static void showWinGUI(ArrayList<String> winnerDeck) throws IOException {
        // Destroy all the components in the main panel
        panel.removeAll();

        // Set the layout manager of the panel to a FlowLayout
        panel.setLayout(new FlowLayout());

        // Set the new size of the window
        root.setSize(new Dimension(1440, 803));

        // Iterate for every card in the winner's deck
        for (int cardNum = 0; cardNum < winnerDeck.size(); cardNum++) {
            // Calculate the file path of the image through concatenation
            String cardImageFilePath = "images/" + winnerDeck.get(cardNum) + ".png";
            // Get the image
            BufferedImage cardImage = ImageIO.read(new File(cardImageFilePath));

            // Display the image at coordinates (cardNum + 10, 10) as a new JLabel with the card image as its
            // image icon

            panel.add(new JLabel(new ImageIcon(cardImage)));
        }

        // Add the panel to root
        root.getContentPane().add(panel);

        // Refresh the window
        root.revalidate();
        root.repaint();
    }
}
