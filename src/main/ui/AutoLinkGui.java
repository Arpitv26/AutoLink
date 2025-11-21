package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Build;
import model.Part;
import persistence.BuildData;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Represents the Swing-based graphical user interface for the AutoLink application.
 * Displays the current build's parts, allows the user to add and filter parts,
 * and provides menu actions for saving and loading application state.
 *
 * The GUI wraps the existing Build model and JSON persistence classes without
 * removing or changing core model functionality, model package functionality is unchanged
 */

@ExcludeFromJacocoGeneratedReport
public class AutoLinkGui {

    private static final String JSON_STORE = "./data/autolink.json";

    // core model & persistence
    private Build build;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Swing components
    private JFrame frame;
    private JLabel logoLabel;
    private PartsPanel partsPanel;
    private JTextArea activeSummaryArea;


    // REQUIRES: Swing must be initialized on the EDT if called from another GUI context.
    // MODIFIES: this
    // EFFECTS:  constructs a new AutoLinkGui with an empty Build, initializes
    //           JSON reader/writer, creates all Swing components, and prepares
    //           the main window (but does not necessarily make it visible yet).
    public AutoLinkGui() {
         // initialize the model and persistence
        build = new Build();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // initialize Swing components
        frame = new JFrame("AutoLink");
        logoLabel = new JLabel();
        partsPanel = new PartsPanel();

        // build the frame + UI
        initFrame();

        // populate the list (build is empty at start, so this just clears)
        refreshPartsList();
        refreshActiveBuildSummary();
    }

    // REQUIRES: this object has been constructed.
    // MODIFIES: this
    // EFFECTS:  configures basic properties of the main JFrame (title, size,
    //           close operation, layout) and attaches the main content panel
    //           and menu bar to the frame.
    private void initFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        initMenuBar();      // File → Load / Save / Quit
        initMainPanel();    // logo + (controls + list)

        frame.setLocationRelativeTo(null); // center on screen
    }

    // REQUIRES: frame != null
    // MODIFIES: this, frame
    // EFFECTS:  creates and installs the menu bar for the main window, including
    //           "File" menu items for Load, Save, and Quit. Wires menu items to
    //           the appropriate handler methods (handleLoad, handleSave, handleQuit).
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> handleLoad());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> handleSave());

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e -> handleQuit());

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(quitItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    // REQUIRES: frame != null
    // MODIFIES: this, frame
    // EFFECTS:  creates the main content panel that includes:
    //           - a logo image at the top (visual component),
    //           - a panel in the center showing the parts currently in the build,
    //           - controls above the parts list for adding and filtering parts.
    //           Adds this content panel to the frame.
    private void initMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // TOP: logo image
        loadLogoImage();  // try to load image
        JLabel topLogo = logoLabel;
        topLogo.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(topLogo, BorderLayout.NORTH);

        // CENTER panel for controls + parts panel
        JPanel centerPanel = new JPanel(new BorderLayout());

        // control row – Add + Set Active + Clear Active
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add Part");
        addButton.addActionListener(e -> handleAddPart());

        JButton setActiveButton = new JButton("Set Active");
        setActiveButton.addActionListener(e -> handleSetActiveFromSelection());

        JButton clearActiveButton = new JButton("Clear Active");
        clearActiveButton.addActionListener(e -> handleClearActiveFromSelection());

        controls.add(addButton);
        controls.add(setActiveButton);
        controls.add(clearActiveButton);

        centerPanel.add(controls, BorderLayout.NORTH);

        // PartsPanel (handles filter + list)
        centerPanel.add(partsPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // BOTTOM: Active build summary
        activeSummaryArea = new JTextArea(8, 50);
        activeSummaryArea.setEditable(false);
        activeSummaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane activeScroll = new JScrollPane(activeSummaryArea);

        JPanel activePanel = new JPanel(new BorderLayout());
        activePanel.setBorder(BorderFactory.createTitledBorder("Current Active Build"));
        activePanel.add(activeScroll, BorderLayout.CENTER);

        mainPanel.add(activePanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
    }

    // REQUIRES: build != null, partsListModel != null
    // MODIFIES: this, partsListModel
    // EFFECTS:  clears the list model and repopulates it with a textual view
    //           of all parts currently in the build's inventory. Each list entry
    //           includes at least the part name, category, and cost.
    private void refreshPartsList() {
        List<Part> parts = build.listAllParts();
        partsPanel.refresh(parts);
    }

    // REQUIRES: build != null
    // MODIFIES: this, build
    // EFFECTS:  shows a dialog that prompts the user for part details (category,
    //           name, cost, and other fields as needed). If the user completes
    //           the form with valid data, constructs the appropriate Part,
    //           adds it to the build, and refreshes the parts list. If the user
    //           cancels or enters invalid data, no changes are made to the build.
    private void handleAddPart() {
        AddPartDialog dialog = new AddPartDialog(frame);
        dialog.showDialog();
        Part newPart = dialog.getResultPart();

        if (newPart != null) {
            try {
                boolean added = build.addPart(newPart);
                if (!added) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "A part with that name already exists in the build.",
                            "Duplicate Part Name",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    refreshPartsList();
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                        frame,
                        e.getMessage(),
                        "Error Adding Part",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    // REQUIRES: jsonWriter != null
    // MODIFIES: this, file system at JSON_STORE
    // EFFECTS:  saves the current build and its parts inventory to JSON_STORE
    //           using JsonWriter. If the file cannot be opened, shows an error
    //           dialog and leaves the current build unchanged.
    private void handleSave() {
       try {
            BuildData data = new BuildData(build, build.getParts());
            jsonWriter.open();
            jsonWriter.write(data);
            jsonWriter.close();

            JOptionPane.showMessageDialog(
                    frame,
                    "Saved AutoLink data to " + JSON_STORE,
                    "Save Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Unable to write to file: " + JSON_STORE,
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // REQUIRES: jsonReader != null
    // MODIFIES: this, build
    // EFFECTS:  attempts to load build and inventory data from JSON_STORE using
    //           JsonReader. If successful, replaces the current Build with the
    //           loaded one and refreshes the parts list. If an error occurs
    //           (e.g., file not found or invalid JSON), shows an error dialog
    //           and leaves the current build unchanged.
    private void handleLoad() {
        try {
            BuildData data = jsonReader.read();
            build = data.getBuild();
            refreshPartsList();
            refreshActiveBuildSummary();

            JOptionPane.showMessageDialog(
                    frame,
                    "Loaded AutoLink data from " + JSON_STORE,
                    "Load Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Unable to read from file: " + JSON_STORE,
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // REQUIRES: logoLabel != null
    // MODIFIES: this, logoLabel
    // EFFECTS:  attempts to load a logo image from the data directory
    //           (for example "./data/autolink_logo.png") and sets it as the icon
    //           on logoLabel. If the image cannot be loaded, logoLabel is left
    //           with a simple text fallback.
    private void loadLogoImage() {
        ImageIcon icon = new ImageIcon("./data/autolink_logo.png");

        if (icon.getIconWidth() == -1) {
            logoLabel.setText("AutoLink");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        } else {
            logoLabel.setIcon(icon);
        }
    }

    // REQUIRES: this object has been constructed and initFrame has been called.
    // MODIFIES: this, frame
    // EFFECTS:  makes the main GUI window visible to the user.
    public void showGui() {
        frame.setVisible(true);
    }

    // REQUIRES: frame != null
    // MODIFIES: this, frame
    // EFFECTS:  closes the main window and terminates the application.
    //           In the future, this method could be extended to prompt the user
    //           to save before exiting.
    private void handleQuit() {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to quit AutoLink?",
                "Confirm Quit",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    // REQUIRES: partsPanel != null
    // EFFECTS:  sets the selected part as active for its category in the Build
    //           and refreshes the active build summary.
    private void handleSetActiveFromSelection() {
        Part selected = partsPanel.getSelectedPart();
        if (selected == null) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Please select a part in the list first.",
                    "No Selection",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // category key used by Build (e.g., "wheel", "tire", etc.)
        String categoryKey = selected.getClass().getSimpleName().toLowerCase();

        try {
            boolean ok = build.replaceActivePart(categoryKey, selected.getName());
            if (!ok) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Could not set active part (unknown category or part not found).",
                        "Set Active",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                refreshActiveBuildSummary();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    e.getMessage(),
                    "Error Setting Active Part",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // REQUIRES: partsPanel != null
    // EFFECTS:  clears the active selection for the selected part's category, if any.
    private void handleClearActiveFromSelection() {
        Part selected = partsPanel.getSelectedPart();
        if (selected == null) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Please select a part whose category you want to clear.",
                    "No Selection",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        String categoryKey = selected.getClass().getSimpleName().toLowerCase();

        try {
            boolean ok = build.clearActive(categoryKey);
            if (!ok) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Unknown category: " + categoryKey,
                        "Clear Active",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                refreshActiveBuildSummary();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    e.getMessage(),
                    "Error Clearing Active Part",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // EFFECTS: rebuilds the multi-line active build summary and shows it
    //          in activeSummaryArea.
    private void refreshActiveBuildSummary() {
        if (activeSummaryArea == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Current Active Build\n");
        sb.append("---------------------\n");

        appendActiveLine(sb, "Wheel", "wheel");
        appendActiveLine(sb, "Tire", "tire");
        appendActiveLine(sb, "Suspension", "suspension");
        appendActiveLine(sb, "Exhaust", "exhaust");
        appendActiveLine(sb, "Engine", "engine");
        appendActiveLine(sb, "Transmission", "transmission");
        appendActiveLine(sb, "Bumper", "bumper");
        appendActiveLine(sb, "SideSkirts", "sideskirts");
        appendActiveLine(sb, "Diffuser", "diffuser");
        appendActiveLine(sb, "Spoiler", "spoiler");
        appendActiveLine(sb, "Lights", "lights");

        sb.append("\n---------------------\n");
        sb.append("Estimated total cost: ").append(build.totalCost());

        activeSummaryArea.setText(sb.toString());
        activeSummaryArea.setCaretPosition(0);
    }

    //helper
    private void appendActiveLine(StringBuilder sb, String label, String key) {
        Part p = build.getActive(key);
        String name = (p == null) ? "-" : p.getName();
        String cost = (p == null) ? "-" : String.valueOf(p.getCost());
        sb.append(String.format("%-12s: %s (cost %s)%n", label, name, cost));
    }



}
