package com.runemate.geashawscripts.LazyChaosDruids.gui;

import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by Ozzy on 10-2-2015.
 */
public class Controller {

    // Because we link this controller to our fxml in the loader class,
    // we can access fxml objects on the gui using the @Fxml annotation.
    @FXML
    private ComboBox<String> cmbFood;
    @FXML
    private Slider sliderHealPercentage;
    @FXML
    private Button btnStart;
    @FXML
    private TextField txtFoodAmount;
    @FXML
    private CheckBox chkAirRune, chkLawRune, chkNatureRune, chkGuam, chkMarrentill,
            chkTarromin, chkHarralander, chkRanarr, chkToadflax, chkIrit, chkAvantoe,
            chkKwuarm, chkSnapdragon, chkCadantine, chkLantadyme, chkDwarfweed;

    public void startController() {

        // Create a new String array for the hide names.
        String[] cmbFoodItems = new String[]{"Trout", "Salmon", "Tuna", "Lobster"};
        // Add the above strings to the cmbHide ComboBox.
        cmbFood.getItems().addAll(cmbFoodItems);

        // Set the action event.
        btnStart.setOnAction(event -> {

            // Get the food from the combobox.
            if (!cmbFood.getSelectionModel().isEmpty()) {
                // Set the food name as static constant.
                LazyChaosDruids.foodName = cmbFood.getSelectionModel().getSelectedItem();
            }
            // Get the percentage to heal at from slider.
            if (sliderHealPercentage != null) {
                // Set the percentage as static constant.
                LazyChaosDruids.healPercentage = (int) sliderHealPercentage.getValue();
            }
            if (txtFoodAmount != null) {
                LazyChaosDruids.foodAmount = Integer.parseInt(txtFoodAmount.getText());
            }

            if (chkAirRune.isSelected()) {
                LazyChaosDruids.lootList.add("Air rune");
                LazyChaosDruids.lootAirRune = true;
            }
            if (chkLawRune.isSelected()) {
                LazyChaosDruids.lootList.add("Law rune");
                LazyChaosDruids.lootLawRune = true;
            }
            if (chkNatureRune.isSelected()) {
                LazyChaosDruids.lootList.add("Nature rune");
                LazyChaosDruids.lootNatureRune = true;
            }
            if (chkGuam.isSelected()) {
                LazyChaosDruids.lootList.add("Guam");
                LazyChaosDruids.lootGuam = true;
            }
            if (chkMarrentill.isSelected()) {
                LazyChaosDruids.lootList.add("Marrentill");
                LazyChaosDruids.lootMarrentill = true;
            }
            if (chkTarromin.isSelected()) {
                LazyChaosDruids.lootList.add("Tarromin");
                LazyChaosDruids.lootTarromin = true;
            }
            if (chkHarralander.isSelected()) {
                LazyChaosDruids.lootList.add("Harralander");
                LazyChaosDruids.lootHarralander = true;
            }
            if (chkRanarr.isSelected()) {
                LazyChaosDruids.lootList.add("Ranarr");
                LazyChaosDruids.lootRanarr = true;
            }
            if (chkToadflax.isSelected()) {
                LazyChaosDruids.lootList.add("Toadflax");
                LazyChaosDruids.lootToadflax = true;
            }
            if (chkIrit.isSelected()) {
                LazyChaosDruids.lootList.add("Irit");
                LazyChaosDruids.lootToadflax = true;
            }
            if (chkAvantoe.isSelected()) {
                LazyChaosDruids.lootList.add("Avantoe");
                LazyChaosDruids.lootAvantoe = true;
            }
            if (chkKwuarm.isSelected()) {
                LazyChaosDruids.lootList.add("Kwuarm");
                LazyChaosDruids.lootKwuarm = true;
            }
            if (chkSnapdragon.isSelected()) {
                LazyChaosDruids.lootList.add("Snapdragon");
                LazyChaosDruids.lootSnapdragon = true;
            }
            if (chkCadantine.isSelected()) {
                LazyChaosDruids.lootList.add("Cadantine");
                LazyChaosDruids.lootCadantine = true;
            }
            if (chkLantadyme.isSelected()) {
                LazyChaosDruids.lootList.add("Lantadyme");
                LazyChaosDruids.lootLantadyme = true;
            }
            if (chkDwarfweed.isSelected()) {
                LazyChaosDruids.lootList.add("Dwarf weed");
                LazyChaosDruids.lootDwarfWeed = true;
            }

            // Make the GUI disappear.
            LazyChaosDruids.guiOpen = false;
            // Close the gui.
            Loader.guiStage.close();
        });
    }
}
