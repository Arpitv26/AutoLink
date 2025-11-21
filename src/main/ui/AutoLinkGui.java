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
    private DefaultListModel<String> partsListModel;
    private JList<String> partsList;
    private JComboBox<String> filterComboBox;
    private JLabel logoLabel;

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
        partsListModel = new DefaultListModel<>();
        partsList = new JList<>(partsListModel);
        filterComboBox = new JComboBox<>(new String[]{
                "All", "Wheel", "Tire", "Suspension", "Exhaust", "Engine",
                "Transmission", "Bumper", "SideSkirts", "Diffuser", "Spoiler", "Lights"
        });
        logoLabel = new JLabel();

        // build the frame + UI
        initFrame();

        // populate the list (build is empty at start, so this just clears)
        refreshPartsList();
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

        // CENTER panel for controls + parts list
        JPanel centerPanel = new JPanel(new BorderLayout());

        // control row
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add Part");
        addButton.addActionListener(e -> handleAddPart());

        JButton filterButton = new JButton("Apply Filter");
        filterButton.addActionListener(e -> handleFilter());

        controls.add(new JLabel("Filter:"));
        controls.add(filterComboBox);
        controls.add(filterButton);
        controls.add(addButton);

        centerPanel.add(controls, BorderLayout.NORTH);

        // parts list scroll panel
        JScrollPane scrollPane = new JScrollPane(partsList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);
    }

    // REQUIRES: build != null, partsListModel != null
    // MODIFIES: this, partsListModel
    // EFFECTS:  clears the list model and repopulates it with a textual view
    //           of all parts currently in the build's inventory. Each list entry
    //           includes at least the part name, category, and cost.
    private void refreshPartsList() {
        partsListModel.clear();

        List<Part> parts = build.listAllParts();
        for (Part p : parts) {
            partsListModel.addElement(formatPartForDisplay(p));
        }
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

    // REQUIRES: build != null, filterComboBox != null, partsListModel != null
    // MODIFIES: this, partsListModel
    // EFFECTS:  reads the selected category from the filter combo box.
    //           If "All" is selected, displays all parts in the list.
    //           Otherwise, displays only parts whose runtime category
    //           (e.g., Wheel, Tire, etc.) matches the selected category.
    private void handleFilter() {
        String selected = (String) filterComboBox.getSelectedItem();
        partsListModel.clear();

        List<Part> parts = build.listAllParts();

        if (selected == null || "All".equals(selected)) {
            for (Part p : parts) {
                partsListModel.addElement(formatPartForDisplay(p));
            }
        } else {
            for (Part p : parts) {
                String category = p.getClass().getSimpleName();
                if (category.equals(selected)) {
                    partsListModel.addElement(formatPartForDisplay(p));
                }
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

    // REQUIRES: p != null
    // MODIFIES: nothing
    // EFFECTS:  returns a simple string representation of the part including
    //           its name, category, and cost in CAD.
    private String formatPartForDisplay(Part p) {
        String category = p.getClass().getSimpleName();
        return p.getName() + " (" + category + ") - $" + p.getCost();
    }

}
