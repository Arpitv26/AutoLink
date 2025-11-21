package ui;

import javax.swing.SwingUtilities;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Main clas that runs the AutoLink application and starts the UI
@ExcludeFromJacocoGeneratedReport
public class Main {

    // EFFECT: launches the AutoLinkApp
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            AutoLinkGui gui = new AutoLinkGui();
            gui.showGui();
        });
    }  
}
