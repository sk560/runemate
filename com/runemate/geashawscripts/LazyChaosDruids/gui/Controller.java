package com.runemate.geashawscripts.LazyChaosDruids.gui;

import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

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
            if (chkAirRune.isSelected()) {
                LazyChaosDruids.lootList.add("Air rune");
            }
            if (chkLawRune.isSelected()) {
                LazyChaosDruids.lootList.add("Law rune");
            }
            if (chkNatureRune.isSelected()) {
                LazyChaosDruids.lootList.add("Nature rune");
            }
            if (chkGuam.isSelected()) {
                LazyChaosDruids.lootList.add("Guam");
            }
            if (chkMarrentill.isSelected()) {
                LazyChaosDruids.lootList.add("Marrentill");
            }
            if (chkTarromin.isSelected()) {
                LazyChaosDruids.lootList.add("Tarromin");
            }
            if (chkHarralander.isSelected()) {
                LazyChaosDruids.lootList.add("Harralander");
            }
            if (chkRanarr.isSelected()) {
                LazyChaosDruids.lootList.add("Ranarr");
            }
            if (chkToadflax.isSelected()) {
                LazyChaosDruids.lootList.add("Toadflax");
            }
            if (chkIrit.isSelected()) {
                LazyChaosDruids.lootList.add("Irit");
            }
            if (chkAvantoe.isSelected()) {
                LazyChaosDruids.lootList.add("Avantoe");
            }
            if (chkKwuarm.isSelected()) {
                LazyChaosDruids.lootList.add("Kwuarm");
            }
            if (chkSnapdragon.isSelected()) {
                LazyChaosDruids.lootList.add("Snapdragon");
            }
            if (chkCadantine.isSelected()) {
                LazyChaosDruids.lootList.add("Cadantine");
            }
            if (chkLantadyme.isSelected()) {
                LazyChaosDruids.lootList.add("Lantadyme");
            }
            if (chkDwarfweed.isSelected()) {
                LazyChaosDruids.lootList.add("Dwarf weed");
            }

            // Make the GUI disappear.
            LazyChaosDruids.guiOpen = false;
            // Close the gui.
            Loader.guiStage.close();
        });
    }
}
