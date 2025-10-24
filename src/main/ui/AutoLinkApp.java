package ui;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.*;

// representation of the console based UI interface for AutoLink through user interaction with console-based inputs
// follows phase 1 user stories where user can add, remove, 
// list parts, and manage active selections to get a summary etc


public class AutoLinkApp {
    private Scanner sc;
    private Build build;
    private boolean running;

    // MODIFIES: this
    // EFFECTS: constructs a new AutoLinkApp with an empty Build and initializes sc 
    //          while also setting running boolean to true
    public AutoLinkApp() {
        build = new Build();
        sc = new Scanner(System.in);
        running = true;   
    }

    // MODIFIES: this
    // EFFECTS: runs the main application and displays initial message and
    //          loop until the user chooses to quit (if user seletess q for quit)
    public void run() {
        System.out.println("Weldocme to AutoLink!");
        divider();

        //loop for the game with ending condition as user input = q which would turn running to false
        while (running) {
            String input = displayMainMenu();

            if (input.equalsIgnoreCase("q")) {
                quit();
            } else if (input.equals("1")) {
                handleInventoryMenu();
            } else if (input.equals("2")) {
                handleActiveBuildMenu();
            } else if (input.equals("3")) {
                handleReportsMenu();
            } else if (input.equals("4")) {
                printHelp();  
            } else {
                System.out.println("Please select only commands from the options displayed!");
            }


        }
    }

    // MODIFIES: this
    // EFFECTS: displays the main menu and returns user input
    private String displayMainMenu() {
        divider();
        System.out.println("AutoLink Main Menu");
        divider();
        System.out.println("1. Inventory");
        System.out.println("2. Active Build");
        System.out.println("3. Reports");
        System.out.println("4. Help");
        System.out.println("Q. Quit");
        System.out.print("Please select a command you would like to perform: ");

        return readInput();

    }

    // MODIFIES: this
    // EFFECTS: handles the inventory menu to show the options which are add remove and list parts
    private void handleInventoryMenu() {
        boolean menu = true;

        while (menu) {
            divider();
            System.out.println("Inventory Menu");
            System.out.println("1. Add a part\n2. Remove a part by name\n3. List all parts\nB. Back\nQ. Quit");

            System.out.print("Select an option: ");
            String input = readInput();
            switch (input.toLowerCase()) {
                case "1": addPart(); 
                    break;
                case "2": removePart(); 
                    break;
                case "3": listParts(); 
                    break;
                case "b": menu = false; 
                    break;
                case "q": quit(); 
                    menu = false;
                    break;
                default: System.out.println("Invalid command, please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts for details for each part and adds a part to inventory
    private void addPart() {
        divider();
        System.out.println("Add a Part Menu");
        divider();
        System.out.println("Categories: wheel, tire, suspension, exhaust, engine, transmission,");
        System.out.println("            bumper, sideskirts, diffuser, spoiler, lights");
        String category = readNonEmpty("Enter category: ").toLowerCase();
        String name = readNonEmpty("Enter part name: ");
        int cost = readInt("Enter cost (whole number): ");

        try {
            Part p = createPartFromInput(category, name, cost);
            if (p == null) {
                System.out.println("Unknown category please try again!");
                return;
            }
            build.addPart(p);
            System.out.println("Added " + p.getName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // REQUIRES: category, name non-empty and cost >= 0
    // MODIFIES: this
    // EFFECTS: creates and returns a part based on category using helpers for each category to keep everything under
    //          25 lines, and also returns null if unknown
    private Part createPartFromInput(String category, String name, int cost) {
        switch (category) {
            case "wheel": return createWheelFromInput(name, cost);
            case "tire": return createTireFromInput(name, cost);
            case "suspension": return createSuspensionFromInput(name, cost);
            case "exhaust": return createExhaustFromInput(name, cost);
            case "engine": return createEngineFromInput(name, cost);
            case "transmission": return createTransmissionFromInput(name, cost);
            case "bumper": return createBumperFromInput(name, cost);
            case "sideskirts": return createSideSkirtsFromInput(name, cost);
            case "diffuser": return createDiffuserFromInput(name, cost);
            case "spoiler": return createSpoilerFromInput(name, cost);
            case "lights": return createLightsFromInput(name, cost);
            default: return null;
        }
    }


    // MODIFIES: this
    // EFFECTS: prompts for a name and removes the matching part if present
    private void removePart() {
        String name = readNonEmpty("Enter the part name to remove: ");
        boolean removed;
        try {
            removed = build.removePartByName(name);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (removed) {
            System.out.println("Removed " + name + ".");
        } else {
            System.out.println("No part named " + name + " was found");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists all parts currently in inventory
    private void listParts() {
        List<Part> parts = build.listAllParts();
        if (parts.isEmpty()) {
            System.out.println("No parts in inventory.!");
            return;
        }

        divider();
        System.out.println("Name | Category | Cost");
        divider();

        for (Part p : parts) {
            String name = p.getName();
            String category = p.getClass().getSimpleName();
            String cost = String.valueOf(p.getCost());
            System.out.println(name + " | " + category + " | " + cost);
        }
    }


    // MODIFIES: this
    // EFFECTS: handles the active build menu options which are set clear and show
    private void handleActiveBuildMenu() {
        boolean inMenu = true;

        while (inMenu) {
            divider();
            System.out.println("Active Build Menu");
            divider();
            System.out.println("1. Set active part\n2. Clear an active part\n3. Show current active build\nB. Back");
            System.out.print("Q. Quit\nSelect an option: ");

            String choice = readInput();

            switch (choice.toLowerCase()) {
                case "1": setActivePartFlow();
                    break;
                case "2": clearActivePartFlow();
                    break;
                case "3": showCurrentActiveBuild();
                    break;
                case "b": inMenu = false;
                    break;
                case "q": quit(); 
                    inMenu = false;
                    break;
                default: System.out.println("Invalid selection please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts for category + part name and sets that part active in its category
    private void setActivePartFlow() {
        System.out.println("Categories: wheel, tire, suspension, exhaust, engine, transmission,");
        System.out.println("            bumper, sideskirts, diffuser, spoiler, lights");
        String category = readNonEmpty("Enter category to set: ").toLowerCase();
        String name = readNonEmpty("Enter part name to activate: ");
        try {
            boolean bool = build.replaceActivePart(category, name);
            if (bool) {
                System.out.println("Active selection updated.");
            } else {
                System.out.println("No matching part (name/type) or unknown category.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: prints a summary of current actives, total cost, and any fitment warnings
    private void showCurrentActiveBuild() {
        divider();
        System.out.println("Current Active Build");
        divider();

        printActive("Wheel", build.getActive("wheel"));
        printActive("Tire", build.getActive("tire"));
        printActive("Suspension", build.getActive("suspension"));
        printActive("Exhaust", build.getActive("exhaust"));
        printActive("Engine", build.getActive("engine"));
        printActive("Transmission", build.getActive("transmission"));
        printActive("Bumper", build.getActive("bumper"));
        printActive("SideSkirts", build.getActive("sideskirts"));
        printActive("Diffuser", build.getActive("diffuser"));
        printActive("Spoiler", build.getActive("spoiler"));
        printActive("Lights", build.getActive("lights"));

        divider();
        System.out.println("Estimated total cost: " + build.totalCost());
    }


    // MODIFIES: this
    // EFFECTS: prompts for a category and clears its active selection
    private void clearActivePartFlow() {
        System.out.println("Categories: wheel, tire, suspension, exhaust, engine, transmission,");
        System.out.println("            bumper, sideskirts, diffuser, spoiler, lights");
        String category = readNonEmpty("Enter category to clear: ").toLowerCase();

        try {
            boolean ok = build.clearActive(category);
            if (ok) {
                System.out.println("Active selection cleared.");
            } else {
                System.out.println("Unknown category.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    // MODIFIES: this
    // EFFECTS: handles the reports menu options like summary or totals, or search tools
    private void handleReportsMenu() {
        divider();
        System.out.println("Reports");
        divider();
        Map<String, Integer> counts = build.countsByCategory();
        if (counts.isEmpty()) {
            System.out.println("No parts in inventory.");
        } else {
            counts.forEach((k,v) -> System.out.println(k + ": " + v));
        }
        System.out.println("Active total cost: " + build.totalCost());                                             
         
    }

    // MODIFIES: this
    // EFFECTS: prints help/instructions for the user (nice to have to guide users through valid inputs)
    private void printHelp() {
        divider();
        System.out.println("Use number keys and 'B' = back or 'Q' = quit");
        System.out.println("Inventory = you can add remove or list all parts");
        System.out.println("Active Build = you can set clear or show all actives");                                
    }

    // MODIFIES: this
    // EFFECTS: reads and returns a line of user input and if there nothing to read for any reason then quit
    private String readInput() {
        if (sc.hasNextLine()) {
            return sc.nextLine().trim();
        }
        return "q";
    }

    // MODIFIES: this
    // EFFECTS: shuts down the application
    private void quit() {
        running = false;
        divider();
        System.out.println("Thank you for using AutoLink, where ideas meet creation!");
        divider();
    }


    //                                       helper methods
    //-------------------------------------------------------------------------------------------------
    

    //helper method to print out the divider (--------)
    private void divider() {
        System.out.println("---------------------");
    }

    //helpers for createPartFromInput

    // Wheels
    private Part createWheelFromInput(String name, int cost) {
        double diameter = readDouble("Diameter: ");
        double width = readDouble("Width: ");
        int offset = readInt("Offset: ");
        return new Wheel(name, cost, diameter, width, offset);
    }

    // Tires
    private Part createTireFromInput(String name, int cost) {
        int width = readInt("Width: ");
        int aspect = readInt("Aspect ratio: ");
        double rim = readDouble("Rim diameter: ");
        return new Tire(name, cost, width, aspect, rim);
    }

    // Suspension
    private Part createSuspensionFromInput(String name, int cost) {
        String type = readNonEmpty("Type: ");
        int drop = readInt("Drop: ");
        return new Suspension(name, cost, type, drop);
    }

    // Exhaust
    private Part createExhaustFromInput(String name, int cost) {
        String spec = readNonEmpty("Spec: ");
        return new Exhaust(name, cost, spec);
    }

    // Engine
    private Part createEngineFromInput(String name, int cost) {
        String type = readNonEmpty("Engine type: ");
        int hp = readInt("Horsepower: ");
        double disp = readDouble("Displacement: ");
        return new Engine(name, cost, type, hp, disp);
    }

    // Transmission
    private Part createTransmissionFromInput(String name, int cost) {
        String type = readNonEmpty("Transmission type: ");
        int gears = readInt("Gears: ");
        String drive = readNonEmpty("Drive: ");
        return new Transmission(name, cost, type, gears, drive);

    }

    // Bumper
    private Part createBumperFromInput(String name, int cost) {
        String type = readNonEmpty("Enter type: ");
        String material = readNonEmpty("Enter material: ");
        String brand = readNonEmpty("Enter brand: ");
        String style = readNonEmpty("Enter style: ");
        return new Bumper(name, cost, type, material, brand, style);
    }

    // SideSkirts
    private Part createSideSkirtsFromInput(String name, int cost) {
        String material = readNonEmpty("Enter material: ");
        String brand = readNonEmpty("Enter brand: ");
        return new SideSkirts(name, cost, material, brand);
    }

    // Diffuser
    private Part createDiffuserFromInput(String name, int cost) {
        String material = readNonEmpty("Enter material: ");
        String brand = readNonEmpty("Enter brand: ");
        boolean functional = readBoolean("Is it functional (affects aerodynamics)? ");
        return new Diffuser(name, cost, material, brand, functional);
    }

    // Spoiler
    private Part createSpoilerFromInput(String name, int cost) {
        String material = readNonEmpty("Enter material: ");
        String style = readNonEmpty("Enter style: ");
        double height = readDouble("Enter height (mm): ");
        return new Spoiler(name, cost, material, style, height);
    }

    // Lights
    private Part createLightsFromInput(String name, int cost) {
        String type = readNonEmpty("Enter type: ");
        String brand = readNonEmpty("Enter brand: ");
        String lightType = readNonEmpty("Enter light type: ");
        String detail = readNonEmpty("Enter detail: ");
        return new Lights(name, cost, type, brand, lightType, detail);
    }

    // REQUIRES: prompt != null
    // MODIFIES: this
    // EFFECTS: prompts until user enters a whole number and returns it
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = readInput();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number only!");
            }
        }
    }

    // REQUIRES: prompt != null
    // MODIFIES: this
    // EFFECTS: prompts until user enters a decimal number and returns it
    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = readInput();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
    }


    // REQUIRES: prompt != null
    // MODIFIES: this
    // EFFECTS: prompts until user enters non empty text and returns it
    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = readInput();
            if (s != null && !s.trim().isEmpty()) {
                return s.trim();
            }
            System.out.println("Input cannot be empty!");
        }
    }

    // REQUIRES: prompt != null
    // MODIFIES: this
    // EFFECTS: prompts until user enters yes/no (y/n); returns true for yes
    private boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String s = readInput().toLowerCase();
            if (s.equals("y") || s.equals("yes")) {
                return true;
            } else if (s.equals("n") || s.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' or 'n'.");
            }
        }
    }

    // REQUIRES: label != null
    // MODIFIES: this
    // EFFECTS: prints one active line safely 
    private void printActive(String label, Part p) {
        String name;
        String cost;

        if (p == null) {
            name = "-";
        } else {
            name = p.getName();
        }

        if (p == null) {
            cost = "-";
        } else {
            cost = p.getName();
        }

        System.out.println(label + ": " + name + " (cost " + cost + ")");
    }
}
