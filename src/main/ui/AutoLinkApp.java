package ui;

import java.util.Scanner;
import model.Build;

// representation of the console based UI interface for AutoLink through user interaction with console-based inputs
// follows phase 1 user stories where user can add, remove, list parts, and manage active selections to get a summary etc...


public class AutoLinkApp {
    private Scanner input;
    private Build build;
    private boolean running;

    // MODIFIES: this
    // EFFECTS: constructs a new AutoLinkApp with an empty Build and initializes input
    public AutoLinkApp() {
        // stub
    }


    // MODIFIES: this
    // EFFECTS: runs the main application loop until the user chooses to quit
    public void run() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: displays the main menu and processes user input
    private void displayMainMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: handles the inventory menu options which are add remove list and find
    private void handleInventoryMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: handles the active build menu options which are set clear and show
    private void handleActiveBuildMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: handles the reports menu options like summary or totals, or search tools
    private void handleReportsMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: prints help/instructions for the user (nice to have to guide users through valid inputs)
    private void printHelp() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: reads and returns a line of user input
    private String readInput() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: shuts down the application
    private void quit() {
        // stub
    }
}
