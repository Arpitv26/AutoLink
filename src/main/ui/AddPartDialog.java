package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// Represents a modal dialog that allows the user to enter details for a new Part.
// The dialog lets the user choose a part category (Wheel, Tire, Suspension, Exhaust,
// Engine, Transmission, Bumper, SideSkirts, Diffuser, Spoiler, Lights), enter a
// name and cost, and fill in any additional category-specific fields. When the
// user confirms with "OK", the dialog attempts to construct the appropriate Part
// subclass. If construction succeeds, the resulting Part can be retrieved by the
// caller; if the user cancels or an error occurs, no Part is returned.
//
// This class is responsible only for gathering input and creating a Part instance.
// It does not modify the Build or any other model objects directly.
@ExcludeFromJacocoGeneratedReport
public class AddPartDialog extends JDialog {
    // common controls
    private JComboBox<String> categoryComboBox;
    private JTextField nameField;
    private JTextField costField;

    // panel that will host category-specific input fields (e.g., diameter, width, etc.)
    private JPanel categoryFieldsPanel;

    // buttons
    private JButton okButton;
    private JButton cancelButton;

    // result Part created when the user clicks OK (null if cancelled or invalid)
    private Part resultPart;

    // stores references to category-specific text fields by logical key
    private Map<String, JTextField> fieldMap;

    // checkbox for boolean field (currently used for Diffuser.functional)
    private JCheckBox functionalCheckBox;

    private final JFrame parent;

    // REQUIRES: parent is non-null; this constructor is called on the Swing
    //           event dispatch thread. The application should treat this dialog
    //           as modal (blocking) with respect to the parent frame.
    // MODIFIES: this
    // EFFECTS:  constructs a modal AddPartDialog owned by the given parent frame,
    //           initializes all Swing components (category selector, common fields
    //           for name and cost, dynamic panel for category-specific fields, OK
    //           and Cancel buttons), sets up listeners for the controls, and
    //           prepares (but does not show) the dialog window.
    public AddPartDialog(JFrame parent) {

        super(parent, "Add Part", true);
        this.parent = parent;
        this.resultPart = null;
        this.fieldMap = new HashMap<>();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUi();
    }

    // REQUIRES: this has been constructed; categoryComboBox, nameField,
    //           costField, categoryFieldsPanel, okButton, and cancelButton
    //           are non-null after execution.
    // MODIFIES: this
    // EFFECTS:  creates and lays out all components in the dialog:
    //           - a combo box to select the part category (Wheel, Tire,
    //             Suspension, Exhaust, Engine, Transmission, Bumper,
    //             SideSkirts, Diffuser, Spoiler, Lights),
    //           - text fields for part name and cost,
    //           - a placeholder panel for category-specific fields,
    //           - OK and Cancel buttons at the bottom.
    //           Also registers listeners so that changing the selected category
    //           rebuilds the category-specific fields and clicking OK or Cancel
    //           calls the appropriate handler methods.
    private void buildUi() {
        setLayout(new BorderLayout(10, 10));

        // top/common fields
        JPanel commonPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        categoryComboBox = new JComboBox<>(new String[]{
                "Wheel", "Tire", "Suspension", "Exhaust", "Engine",
                "Transmission", "Bumper", "SideSkirts", "Diffuser",
                "Spoiler", "Lights"
        });
        nameField = new JTextField();
        costField = new JTextField();

        commonPanel.add(new JLabel("Category:"));
        commonPanel.add(categoryComboBox);
        commonPanel.add(new JLabel("Name:"));
        commonPanel.add(nameField);
        commonPanel.add(new JLabel("Cost (CAD):"));
        commonPanel.add(costField);

        // panel for category-specific fields
        categoryFieldsPanel = new JPanel();
        categoryFieldsPanel.setLayout(new GridLayout(0, 2, 5, 5));

        // buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // center panel: common fields on top, category fields below
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(commonPanel, BorderLayout.NORTH);
        centerPanel.add(categoryFieldsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // listeners
        categoryComboBox.addActionListener(e -> rebuildCategoryFields());
        okButton.addActionListener(e -> handleOk());
        cancelButton.addActionListener(e -> handleCancel());

        // initial category-specific UI
        rebuildCategoryFields();
    }

    // REQUIRES: categoryComboBox != null; categoryFieldsPanel != null
    // MODIFIES: this, categoryFieldsPanel
    // EFFECTS:  clears any existing category-specific fields from the
    //           categoryFieldsPanel and populates it with new input controls
    //           appropriate to the currently selected category. For example:
    //           - Wheel: diameter, width, offset
    //           - Tire: width, aspectPercent, rimDiameterIn
    //           - Suspension: type, dropMm
    //           etc. Does not create or modify any Part instances.
    private void rebuildCategoryFields() {
        categoryFieldsPanel.removeAll();
        fieldMap.clear();
        functionalCheckBox = null;

        String category = (String) categoryComboBox.getSelectedItem();
        if (category == null) {
            categoryFieldsPanel.revalidate();
            categoryFieldsPanel.repaint();
            return;
        }

        // use a simple label-field grid
        categoryFieldsPanel.setLayout(new GridLayout(0, 2, 5, 5));

        switch (category) {
            case "Wheel":
                addLabeledField("Diameter (in):", "diameterIn");
                addLabeledField("Width (in):", "widthIn");
                addLabeledField("Offset (mm):", "offsetMm");
                break;
            case "Tire":
                addLabeledField("Width (mm):", "widthMm");
                addLabeledField("Aspect (%):", "aspectPercent");
                addLabeledField("Rim Diameter (in):", "rimDiameterIn");
                break;
            case "Suspension":
                addLabeledField("Type:", "type");
                addLabeledField("Drop (mm):", "dropMm");
                break;
            case "Exhaust":
                addLabeledField("Spec (brand/model/notes):", "spec");
                break;
            case "Engine":
                addLabeledField("Type:", "type");
                addLabeledField("Horsepower:", "horsepower");
                addLabeledField("Displacement (L):", "displacement");
                break;
            case "Transmission":
                addLabeledField("Type:", "type");
                addLabeledField("Gears:", "gears");
                addLabeledField("Drive (RWD/FWD/AWD):", "drive");
                break;
            case "Bumper":
                addLabeledField("Type (front/rear):", "type");
                addLabeledField("Material:", "material");
                addLabeledField("Brand:", "brand");
                addLabeledField("Style:", "style");
                break;
            case "SideSkirts":
                addLabeledField("Material:", "material");
                addLabeledField("Brand:", "brand");
                break;
            case "Diffuser":
                addLabeledField("Material:", "material");
                addLabeledField("Brand:", "brand");
                categoryFieldsPanel.add(new JLabel("Functional (aero)?"));
                functionalCheckBox = new JCheckBox();
                categoryFieldsPanel.add(functionalCheckBox);
                break;
            case "Spoiler":
                addLabeledField("Material:", "material");
                addLabeledField("Style:", "style");
                addLabeledField("Height (mm):", "heightMm");
                break;
            case "Lights":
                addLabeledField("Type (headlights/taillights):", "type");
                addLabeledField("Brand:", "brand");
                addLabeledField("Light type (LED/HID/etc.):", "lightType");
                addLabeledField("Detail (colour temp/tint):", "detail");
                break;
            default:
                // no fields for unknown category (should not happen)
                break;
        }

        categoryFieldsPanel.revalidate();
        categoryFieldsPanel.repaint();
    }

    // helper: add a label + textfield pair and register in fieldMap
    private void addLabeledField(String labelText, String key) {
        JLabel label = new JLabel(labelText);
        JTextField field = new JTextField();
        categoryFieldsPanel.add(label);
        categoryFieldsPanel.add(field);
        fieldMap.put(key, field);
    }

    // REQUIRES: this dialog has been shown to the user with setVisible(true).
    // MODIFIES: this
    // EFFECTS:  returns the Part created from the user's input if the user
    //           clicked OK and all fields were valid; returns null if the user
    //           cancelled the dialog or if an error occurred preventing Part
    //           creation. This method does not show or hide the dialog itself.
    public Part getResultPart() {
        return resultPart;
    }

    // REQUIRES: this has been constructed and buildUi has been called.
    // MODIFIES: this
    // EFFECTS:  packs the dialog, positions it relative to its parent, and
    //           makes it visible. This call blocks until the user closes the
    //           dialog via OK or Cancel, at which point resultPart may be
    //           either a newly constructed Part or null.
    public void showDialog() {
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    // REQUIRES: all input fields are initialized; user has clicked the OK button.
    // MODIFIES: this
    // EFFECTS:  reads all common and category-specific input fields, validates
    //           the data, and attempts to construct the appropriate Part
    //           subclass based on the selected category. If construction
    //           succeeds, stores the new Part in resultPart and hides the
    //           dialog. If validation fails or an IllegalArgumentException is
    //           thrown by a Part constructor, shows an error message dialog and
    //           leaves the dialog visible so the user can correct the input;
    //           in that case, resultPart remains unchanged.
    private void handleOk() {
        String category = (String) categoryComboBox.getSelectedItem();
        if (category == null) {
            showError("Please select a category.");
            return;
        }

        String name = nameField.getText();
        if (name == null || name.trim().isEmpty()) {
            showError("Name cannot be empty.");
            return;
        }

        try {
            int cost = parseIntField(costField, "Cost");
            Part part = createPartFromInputs(category, name.trim(), cost);
            resultPart = part;
            setVisible(false);
            dispose();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    // REQUIRES: user has clicked the Cancel button or closed the dialog window.
    // MODIFIES: this
    // EFFECTS:  sets resultPart to null and hides/disposes the dialog without
    //           creating or modifying any Part objects.
    private void handleCancel() {
        resultPart = null;
        setVisible(false);
        dispose();
    }

    // REQUIRES: category is a non-null string matching one of the known part
    //           categories ("Wheel", "Tire", "Suspension", "Exhaust", "Engine",
    //           "Transmission", "Bumper", "SideSkirts", "Diffuser", "Spoiler",
    //           "Lights"); all required text fields for that category are
    //           non-null text fields belonging to this dialog.
    // MODIFIES: nothing
    // EFFECTS:  reads the values from the name, cost, and category-specific
    //           text fields and constructs the corresponding Part subclass.
    //           For example, if category is "Wheel", this method constructs
    //           and returns a Wheel using diameter, width, and offset fields.
    //           If any parse fails (e.g., number format) or if a Part
    //           constructor throws IllegalArgumentException, this method
    //           propagates the exception to the caller.
    private Part createPartFromInputs(String category, String name, int cost) {
        switch (category) {
            case "Wheel":
                double diameterIn = parseDoubleField(fieldMap.get("diameterIn"), "Diameter");
                double widthIn = parseDoubleField(fieldMap.get("widthIn"), "Width");
                int offsetMm = parseIntField(fieldMap.get("offsetMm"), "Offset");
                return new Wheel(name, cost, diameterIn, widthIn, offsetMm);

            case "Tire":
                int widthMm = parseIntField(fieldMap.get("widthMm"), "Width");
                int aspectPercent = parseIntField(fieldMap.get("aspectPercent"), "Aspect");
                double rimDiameterIn = parseDoubleField(fieldMap.get("rimDiameterIn"), "Rim Diameter");
                return new Tire(name, cost, widthMm, aspectPercent, rimDiameterIn);

            case "Suspension":
                String suspType = getNonEmptyText(fieldMap.get("type"), "Type");
                int dropMm = parseIntField(fieldMap.get("dropMm"), "Drop");
                return new Suspension(name, cost, suspType, dropMm);

            case "Exhaust":
                String spec = getNonEmptyText(fieldMap.get("spec"), "Spec");
                return new Exhaust(name, cost, spec);

            case "Engine":
                String engType = getNonEmptyText(fieldMap.get("type"), "Type");
                int hp = parseIntField(fieldMap.get("horsepower"), "Horsepower");
                double disp = parseDoubleField(fieldMap.get("displacement"), "Displacement");
                return new Engine(name, cost, engType, hp, disp);

            case "Transmission":
                String transType = getNonEmptyText(fieldMap.get("type"), "Type");
                int gears = parseIntField(fieldMap.get("gears"), "Gears");
                String drive = getNonEmptyText(fieldMap.get("drive"), "Drive");
                return new Transmission(name, cost, transType, gears, drive);

            case "Bumper":
                String bumpType = getNonEmptyText(fieldMap.get("type"), "Type");
                String material = getNonEmptyText(fieldMap.get("material"), "Material");
                String brand = getNonEmptyText(fieldMap.get("brand"), "Brand");
                String style = getNonEmptyText(fieldMap.get("style"), "Style");
                return new Bumper(name, cost, bumpType, material, brand, style);

            case "SideSkirts":
                String ssMaterial = getNonEmptyText(fieldMap.get("material"), "Material");
                String ssBrand = getNonEmptyText(fieldMap.get("brand"), "Brand");
                return new SideSkirts(name, cost, ssMaterial, ssBrand);

            case "Diffuser":
                String diffMaterial = getNonEmptyText(fieldMap.get("material"), "Material");
                String diffBrand = getNonEmptyText(fieldMap.get("brand"), "Brand");
                boolean functional = functionalCheckBox != null && functionalCheckBox.isSelected();
                return new Diffuser(name, cost, diffMaterial, diffBrand, functional);

            case "Spoiler":
                String spMaterial = getNonEmptyText(fieldMap.get("material"), "Material");
                String spStyle = getNonEmptyText(fieldMap.get("style"), "Style");
                double heightMm = parseDoubleField(fieldMap.get("heightMm"), "Height");
                return new Spoiler(name, cost, spMaterial, spStyle, heightMm);

            case "Lights":
                String lType = getNonEmptyText(fieldMap.get("type"), "Type");
                String lBrand = getNonEmptyText(fieldMap.get("brand"), "Brand");
                String lightType = getNonEmptyText(fieldMap.get("lightType"), "Light type");
                String detail = getNonEmptyText(fieldMap.get("detail"), "Detail");
                return new Lights(name, cost, lType, lBrand, lightType, detail);

            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }


    // helper: read non-empty string from a text field
    private String getNonEmptyText(JTextField field, String fieldName) {
        if (field == null) {
            throw new IllegalArgumentException(fieldName + " field is missing.");
        }
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return text.trim();
    }

    // REQUIRES: field is non-null
    // MODIFIES: nothing
    // EFFECTS:  parses the text in the given field as an int and returns it.
    //           If parsing fails (NumberFormatException) or if the text is
    //           empty/blank, throws IllegalArgumentException with a descriptive
    //           error message.
    private int parseIntField(JTextField field, String fieldName) {
        if (field == null) {
            throw new IllegalArgumentException(fieldName + " field is missing.");
        }
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a whole number.");
        }
    }

    // REQUIRES: field is non-null
    // MODIFIES: nothing
    // EFFECTS:  parses the text in the given field as a double and returns it.
    //           If parsing fails (NumberFormatException) or if the text is
    //           empty/blank, throws IllegalArgumentException with a descriptive
    //           error message.
    private double parseDoubleField(JTextField field, String fieldName) {
        if (field == null) {
            throw new IllegalArgumentException(fieldName + " field is missing.");
        }
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a number.");
        }
    }

    // REQUIRES: message is non-null
    // MODIFIES: this
    // EFFECTS:  shows a modal error message dialog attached to this dialog,
    //           displaying the given message. Does not change any fields or
    //           close the dialog.
    private void showError(String message) {
        JOptionPane.showMessageDialog(this,message,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
