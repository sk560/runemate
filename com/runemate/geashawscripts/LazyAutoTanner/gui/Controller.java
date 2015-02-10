package com.runemate.geashawscripts.LazyAutoTanner.gui;

import com.runemate.geashawscripts.LazyAutoTanner.Constants;
import com.runemate.geashawscripts.LazyAutoTanner.LazyAutoTanner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Created by Ozzy on 10-2-2015.
 */
public class Controller {

    // Because we link this controller to our fxml in the loader class,
    // we can access fxml objects on the gui using the @Fxml annotation.
    @FXML
    private ComboBox<String> cmbHide;
    @FXML
    private Button btnStart;

    public void startController() {
        // Create a new String array for the hide names.
        String[] cmbHideItems = new String[]{"Cowhide", "Snake hide", "Green", "Blue", "Red", "Black", "Royal"};
        // Add the above strings to the cmbHide ComboBox.
        cmbHide.getItems().addAll(cmbHideItems);

        // Set the action event.
        btnStart.setOnAction(event -> {

            // Check if the ComboBox for the hide is not empty.
            if (!cmbHide.getSelectionModel().isEmpty()) {

                String selectedItem = cmbHide.getSelectionModel().getSelectedItem();

                if (selectedItem == "Cowhide") {
                    Constants.hide = "Cowhide";
                    Constants.leather = "Leather";
                    Constants.hideId = 1739;
                    Constants.leatherId = 1741;
                } else if (selectedItem == "Snake hide") {
                    Constants.hide = "Snake hide";
                    Constants.leather = "Snakeskin";
                    Constants.hideId = 7801;
                    Constants.leatherId = 6289;
                } else if (selectedItem == "Green dragonhide") {
                    Constants.hide = selectedItem;
                    Constants.leather = "Green dragon leather";
                    Constants.hideId = 1753;
                    Constants.leatherId = 1745;
                } else if (selectedItem == "Blue dragonhide") {
                    Constants.hide = selectedItem;
                    Constants.leather = "Blue dragon Leather";
                    Constants.hideId = 1751;
                    Constants.leatherId = 2205;
                } else if (selectedItem == "Red dragonhide") {
                    Constants.hide = selectedItem;
                    Constants.leather = "Red dragon Leather";
                    Constants.hideId = 1749;
                    Constants.leatherId = 2507;
                } else if (selectedItem == "Black dragonhide") {
                    Constants.hide = selectedItem;
                    Constants.leather = "Black dragon Leather";
                    Constants.hideId = 1747;
                    Constants.leatherId = 2509;
                } else if (selectedItem == "Royal dragonhide") {
                    Constants.hide = selectedItem;
                    Constants.leather = "Black dragon Leather";
                    Constants.hideId = 24372;
                    Constants.leatherId = 24375;
                }
                LazyAutoTanner.guiOpen = false;
                // Close the gui.
                Loader.guiStage.close();
            }
        });
    }
}
