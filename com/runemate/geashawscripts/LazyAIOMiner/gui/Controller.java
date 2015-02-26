package com.runemate.geashawscripts.LazyAIOMiner.gui;

import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ozzy on 10-2-2015.
 */
public class Controller {

    @FXML
    private ComboBox<String> cmbOre;
    @FXML
    private ComboBox<String> cmbArea;
    @FXML
    private Button btnStart;

    public void startController() {

        // Create a new String array for the ore names.
        String[] cmbOreItems = new String[]{"Tin", "Copper"};
        // Add the above strings to the cmbHide ComboBox.
        cmbOre.getItems().addAll(cmbOreItems);

        // Create a new String array for the ore names.
        String[] cmbLocationItems = new String[]{"Varrock"};
        // Add the above strings to the cmbHide ComboBox.
        cmbArea.getItems().addAll(cmbLocationItems);

        // Set the action event.
        btnStart.setOnAction(event -> {

            // Check if the ComboBox for the ore is not empty.
            if (!cmbOre.getSelectionModel().isEmpty()) {
                String ore = cmbOre.getSelectionModel().getSelectedItem();
                LazyAIOMiner.selectedOre = ore;

                Integer[] copper = {13450, 13451, 13452};
                Integer[] tin = {13447, 13448, 13449};

                if (ore == "Copper") {
                    LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(copper));
                    LazyAIOMiner.oreName = "Copper ore";
                    LazyAIOMiner.oreExp = 17.5;
                } else if (ore == "Tin") {
                    LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(tin));
                    LazyAIOMiner.oreName = "Tin ore";
                    LazyAIOMiner.oreExp = 17.5;
                }
            }

            // Check if the ComboBox for the area is not empty.
            if (!cmbArea.getSelectionModel().isEmpty()) {
                String area = cmbArea.getSelectionModel().getSelectedItem();
                LazyAIOMiner.selectedArea = area;
            }

            LazyAIOMiner.guiOpen = false;
            // Close the gui.
            Loader.guiStage.close();
        });
    }
}
