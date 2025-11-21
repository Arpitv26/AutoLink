package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Part;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the panel that displays the parts in the current AutoLink build.
 * Shows a list of parts and provides controls for filtering the list by part
 * category (Wheel, Tire, Suspension, Exhaust, Engine, Transmission, Bumper,
 * SideSkirts, Diffuser, Spoiler, Lights).
 *
 * This class is responsible only for the visual representation and basic
 * filtering of parts; it does not modify the underlying model. The parent
 * GUI (AutoLinkGui) is responsible for supplying the list of parts to display.
 */

@ExcludeFromJacocoGeneratedReport
public class PartsPanel extends JPanel {

    // list model + list used to display parts
    private DefaultListModel<String> partsListModel;
    private JList<String> partsList;

    // filter controls
    private JComboBox<String> filterComboBox;
    private JButton filterButton;

    // cached copy of all parts currently supplied by the GUI
    private List<Part> currentParts;

    // REQUIRES: this panel is created and used on the Swing event dispatch thread.
    // MODIFIES: this
    // EFFECTS:  constructs a PartsPanel with an empty list model, an initially
    //           empty list of parts, a filter combo box pre-populated with
    //           "All" and each concrete part category name (Wheel, Tire,
    //           Suspension, Exhaust, Engine, Transmission, Bumper, SideSkirts,
    //           Diffuser, Spoiler, Lights), and lays out the filter controls
    //           above the scrollable parts list.
    public PartsPanel() {
        currentParts = new ArrayList<>();
        buildUi();
    }

    // REQUIRES: categories is non-null and contains at least "All"
    // MODIFIES: this, filterComboBox
    // EFFECTS:  installs the given list of category names into the filter combo
    //           box, replacing any existing items. This method can be used by
    //           the parent GUI to customize or re-initialize the available
    //           filter options if needed.
    private void setFilterCategories(List<String> categories) {
        filterComboBox.removeAllItems();
        for (String c : categories) {
            filterComboBox.addItem(c);
        }
        if (filterComboBox.getItemCount() > 0) {
            filterComboBox.setSelectedIndex(0);
        }
    }

    // REQUIRES: currentParts != null (may be empty); partsListModel != null
    // MODIFIES: this, partsListModel
    // EFFECTS:  clears the list model and repopulates it with string
    //           representations of all parts in currentParts, ignoring any
    //           category filter. Each entry includes at least the part name,
    //           category (simple class name), and cost.
    private void refreshAllPartsInList() {
        partsListModel.clear();
        for (Part p : currentParts) {
            partsListModel.addElement(formatPartForDisplay(p));
        }
    }

    // REQUIRES: parts != null; parts may be empty
    // MODIFIES: this, currentParts, partsListModel
    // EFFECTS:  replaces the cached list of parts with a defensive copy of
    //           parts, then refreshes the visible list according to the
    //           currently selected filter in the combo box. If "All" is
    //           selected, all parts are shown; otherwise, only parts whose
    //           runtime category matches the selected category are displayed.
    public void refresh(List<Part> parts) {
        currentParts = new ArrayList<>(parts);
        applyFilterFromSelection();
    }

    // REQUIRES: currentParts != null; filterComboBox != null; partsListModel != null
    // MODIFIES: this, partsListModel
    // EFFECTS:  reads the selected category from the filter combo box. If the
    //           selected item is "All", displays all parts in currentParts.
    //           Otherwise, displays only parts whose class simple name matches
    //           the selected category string. The list model is cleared and
    //           repopulated with the filtered entries.
    private void applyFilterFromSelection() {
        if (partsListModel == null || currentParts == null) {
            return;
        }

        String selected = (filterComboBox != null)
                ? (String) filterComboBox.getSelectedItem()
                : null;

        partsListModel.clear();

        if (selected == null || "All".equals(selected)) {
            for (Part p : currentParts) {
                partsListModel.addElement(formatPartForDisplay(p));
            }
        } else {
            for (Part p : currentParts) {
                String category = p.getClass().getSimpleName();
                if (category.equals(selected)) {
                    partsListModel.addElement(formatPartForDisplay(p));
                }
            }
        }
    }

    // REQUIRES: this has been constructed; partsListModel, partsList,
    //           filterComboBox, and filterButton are non-null after execution.
    // MODIFIES: this
    // EFFECTS:  initializes Swing components (list model, list, combo box,
    //           filter button), sets up listeners on the filter button to call
    //           applyFilterFromSelection, and arranges the controls using an
    //           appropriate layout with filter controls above a scroll pane
    //           containing the parts list.
    private void buildUi() {
        setLayout(new BorderLayout());

        partsListModel = new DefaultListModel<>();
        partsList = new JList<>(partsListModel);

        filterComboBox = new JComboBox<>();
        filterButton = new JButton("Apply Filter");

        // set up categories
        List<String> categories = new ArrayList<>();
        categories.add("All");
        categories.add("Wheel");
        categories.add("Tire");
        categories.add("Suspension");
        categories.add("Exhaust");
        categories.add("Engine");
        categories.add("Transmission");
        categories.add("Bumper");
        categories.add("SideSkirts");
        categories.add("Diffuser");
        categories.add("Spoiler");
        categories.add("Lights");
        setFilterCategories(categories);

        filterButton.addActionListener(e -> applyFilterFromSelection());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(new JLabel("Filter:"));
        controls.add(filterComboBox);
        controls.add(filterButton);

        JScrollPane scrollPane = new JScrollPane(partsList);

        add(controls, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    // REQUIRES: part != null
    // MODIFIES: nothing
    // EFFECTS:  returns a human-readable string representation of the given
    //           part for display in the list.
    private String formatPartForDisplay(Part part) {
        String category = part.getClass().getSimpleName();
        return part.getName() + " (" + category + ") - $" + part.getCost();
    }
}
