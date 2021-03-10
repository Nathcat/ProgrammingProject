package com.nathcat.ProgrammingProject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static ArrayList<String> cardDeck = new ArrayList<String>(Arrays.asList(
            "1_r",
            "2_r",
            "3_r",
            "4_r",
            "5_r",
            "6_r",
            "7_r",
            "8_r",
            "9_r",
            "10_r",
            "1_b",
            "2_b",
            "3_b",
            "4_b",
            "5_b",
            "6_b",
            "7_b",
            "8_b",
            "9_b",
            "10_b",
            "1_y",
            "2_y",
            "3_y",
            "4_y",
            "5_y",
            "6_y",
            "7_y",
            "8_y",
            "9_y",
            "10_y"));

    public static ArrayList<String> playerOneDeck = new ArrayList<String>();
    public static ArrayList<String> playerTwoDeck = new ArrayList<String>();

    public static boolean isPlayerOneTurn = true;

    public static void main(String[] args) throws IOException {
        // Create a new AuthenticatorWindow, this will initiate the class using its main() method
        AuthenticatorWindow authenticatorWindow = new AuthenticatorWindow();
        authenticatorWindow.GUI();

        // Loop while login details are invalid
        while (!authenticatorWindow.allowAccess) {
            System.out.println(authenticatorWindow.allowAccess);
        }

        System.out.println("Details valid!");
        // Destroy the authenticator window
        AuthenticatorWindow.root.dispose();

        // Shuffle the deck
        cardDeck = shuffle();

        // Create a new instance of GameWindow
        GameWindow gameWindow = new GameWindow();
        gameWindow.GUI();

        // Play (loop) while the cardDeck still contains cards
        while (cardDeck.size() != 0) {
            System.out.println();
        }

        // Find game winner once the deck is empty
        findGameWinner();

        // Display the top five players
        displayTopFive();
    }

    public static ArrayList<String> shuffle() {
        // Create a new random generator
        Random r = new Random();

        // Create a new array list
        ArrayList<String> newDeck = new ArrayList<String>();

        // Iterate 30 times (once for every card)
        int index;
        for (int x = 0; x < 30; x++) {
            // Select a random index
            index = r.nextInt(cardDeck.size());
            // Add the card at this index to the new list
            newDeck.add(cardDeck.get(index));
            // Remove the card from the original unshuffled deck
            cardDeck.remove(index);
        }

        return newDeck;
    }

    public static void findRoundWinner() {
        // Get the cards at the end of each player's decks
        // these will be the cards that they have just drawn.
        // Then convert these Strings to a string array, so that
        // {number, colour}
        String[] playerOneCard = playerOneDeck.get(playerOneDeck.size() - 1).split("_");
        String[] playerTwoCard = playerTwoDeck.get(playerTwoDeck.size() - 1).split("_");

        // If the colour is the same, highest number wins
        if (playerOneCard[1].contentEquals(playerTwoCard[1])) {
            // highest number wins
            if (Integer.parseInt(playerOneCard[0]) > Integer.parseInt(playerTwoCard[0])) {
                // Player one wins
                // Take player two's last card and add it to player one's deck
                playerOneDeck.add(playerTwoDeck.get(playerTwoDeck.size() - 1));
                // And delete player two's last card
                playerTwoDeck.remove(playerTwoDeck.size() - 1);

                // Show pop-up
                JOptionPane.showMessageDialog(GameWindow.root, "Player one won this round!");

            } else {
                // Player two wins
                // Take player One's last card and add it to player two's deck
                playerTwoDeck.add(playerOneDeck.get(playerOneDeck.size() - 1));
                // And delete player one's last card
                playerOneDeck.remove(playerOneDeck.size() - 1);

                // Show pop-up
                JOptionPane.showMessageDialog(GameWindow.root, "Player two won this round!");
            }

        } else {
            if ((playerOneCard[1].contentEquals("r") && playerTwoCard[1].contentEquals("b"))
            || (playerOneCard[1].contentEquals("y") && playerTwoCard[1].contentEquals("r"))
            || (playerOneCard[1].contentEquals("b") && playerTwoCard[1].contentEquals("y"))) {
                // Player one wins
                // Take player two's last card and add it to player one's deck
                playerOneDeck.add(playerTwoDeck.get(playerTwoDeck.size() - 1));
                // And delete player two's last card
                playerTwoDeck.remove(playerTwoDeck.size() - 1);

                // Show pop-up
                JOptionPane.showMessageDialog(GameWindow.root, "Player one won this round!");

            } else {
                // Player two wins
                // Take player two's last card and add it to player one's deck
                playerTwoDeck.add(playerOneDeck.get(playerOneDeck.size() - 1));
                // And delete player two's last card
                playerOneDeck.remove(playerOneDeck.size() - 1);

                // Show pop-up
                JOptionPane.showMessageDialog(GameWindow.root, "Player two won this round!");
            }
        }
    }

    public static void findGameWinner() throws IOException {
        if (playerOneDeck.size() == playerTwoDeck.size()) {  // Check for tie
            // Show message box
            JOptionPane.showMessageDialog(GameWindow.root, "Its a tie!");
            // Just display a blank window by passing an empty ArrayList to GameWindow.showWinGUI()
            GameWindow.showWinGUI(new ArrayList<String>());

        } else if (playerOneDeck.size() > playerTwoDeck.size()) {  // Check if player one wins
            // Show message box
            JOptionPane.showMessageDialog(GameWindow.root, "Player one wins!");
            // Show player one's deck
            GameWindow.showWinGUI(playerOneDeck);

            // Write player one as the winner
            writeWinner(playerOneDeck);

        } else {  // If none of the above conditions are met, player two has one
            // Show message box
            JOptionPane.showMessageDialog(GameWindow.root, "Player two wins!");

            // Show player two's deck
            GameWindow.showWinGUI(playerTwoDeck);

            // Write player 2 as the winner
            writeWinner(playerTwoDeck);
        }
    }

    public static void writeWinner(ArrayList<String> winnersDeck) throws IOException {
        // Get the winner's name
        String playerName = JOptionPane.showInputDialog("Please enter the winning player's name");

        // Check that the playerName does not include underscores
        // Do this by using .contains() on playerName to get a boolean value for the condition
        if (playerName.contains("_")) {
            // Use the replaceAll() method to replace all underscores with spaces
            playerName = playerName.replaceAll("_", " ");
        }

        // Open winners.txt in a FileWriter
        FileWriter file = new FileWriter("winners.txt", true);

        // Get the size of the deck and cast it to a string
        String deckSize = Integer.toString(winnersDeck.size());

        // Concatenate the player name to the size of their deck
        String string = playerName + "_" + deckSize;

        // Append the string to the file
        file.append(string);

        // Add a newline character to the file
        file.append("\n");

        // Close the file, flushing the current buffer
        file.close();
    }

    private static ArrayList<String> playerNames;
    private static ArrayList<Integer> deckSizes;

    public static void displayTopFive() throws IOException {
        // Open the file
        File file = new File("winners.txt");
        // Create a Scanner to read the file
        Scanner reader = new Scanner(file);

        // Create the list for player names
        playerNames = new ArrayList<String>();
        // Create the list for deck sizes
        deckSizes = new ArrayList<Integer>();

        // Iterate while the file has a next line (reader.hasNextLine())
        while (reader.hasNextLine()) {
            // Read the next line of the file and split the line at the underscore
            String[] data = reader.nextLine().split("_");

            // Add the first value (the name) to the playerNames list
            playerNames.add(data[0]);
            // Add the second value (the deck size) to the deckSizes list
            deckSizes.add(Integer.parseInt(data[1]));
        }

        // Sort the lists
        bubbleSort(deckSizes, playerNames);

        // Get the top five player names
        ArrayList<String> topFiveNames = new ArrayList<String>();
        // Iterate through the positions
        for (int position = 1; position <= 5; position++) {
            // Add a name to the list, working backwards, as the end of the list will be 1st place
            try {
                topFiveNames.add(playerNames.get(playerNames.size() - position));
            } catch (IndexOutOfBoundsException e) {
                // If an index error is thrown, there are less than five players in the file, so add an empty string
                // at the start of the list
                topFiveNames.add("");
            }
        }

        // Get the top five deck sizes
        ArrayList<String> topFiveDeckSizes = new ArrayList<String>();
        // Iterate through the positions
        for (int position = 1; position <= 5; position++) {
            // Add a deck size to the list, working backwards, as the end of the list will be 1st place
            try {
                topFiveDeckSizes.add(Integer.toString(deckSizes.get(deckSizes.size() - position)));
            } catch (IndexOutOfBoundsException e) {
                // If an index error is thrown, add an empty string at the start of the list
                topFiveDeckSizes.add("");
            }
        }

        // Create the top five string, this string will be displayed in the pop-up window later
        String topFiveString = "-----Top five scores-----";
        // Iterate for every item in the top five list, or while index is less than topFiveNames.length
        for (int index = 0; index < topFiveNames.size(); index++) {
            // Calculate the string to add, add a newline character, the current top five player name,
            // a space, and the number of cards they collected.
            String stringToAdd = "\n" + topFiveNames.get(index) + "      " + topFiveDeckSizes.get(index);
            // Add the string to add to the top five string
            topFiveString += stringToAdd;
        }

        // Display the top five in a pop-up window
        JOptionPane.showMessageDialog(GameWindow.root, topFiveString);
    }

    public static void bubbleSort(ArrayList<Integer> integerList, ArrayList<String> stringList) {
        // boolean emptyPass defines whether or not there was a change in the last pass
        // If false, a change was made (not an empty pass)
        // If true, no change was made, and the loop should stop
        boolean emptyPass;

        // Use a do while loop so that there is at lease one iteration, so that emptyPass is defined.
        do {
            // Set empty pass to true as default
            emptyPass = true;

            // Loop for every value in integerList, which will be the same size a stringList
            for (int index = 0; index < integerList.size() - 1; index++) {
                // If the current value is greater than the next value...
                if (integerList.get(index) > integerList.get(index + 1)) {
                    // Set emptyPass to false, so that the loop continues
                    emptyPass = false;

                    // Swap the values in the integerList
                    // Define a temporary variable
                    int temp_integer = integerList.get(index + 1);

                    // Set the next value equal to the current value
                    integerList.set(index + 1, integerList.get(index));

                    // Set the current value equal to the temporary variable, which is the same as the next value
                    integerList.set(index, temp_integer);

                    // Now perform the same actions for the stringList
                    // Define the temporary variable
                    String temp_string = stringList.get(index + 1);

                    // Set the next value equal to the current value
                    stringList.set(index + 1, stringList.get(index));

                    // Set the current value to the temporary variable
                    stringList.set(index, temp_string);
                }
            }

        } while (!emptyPass);

        playerNames = stringList;
        deckSizes = integerList;
    }
}
