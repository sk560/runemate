package com.runemate.geashawscripts.LazyAIOMiner.gui;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javax.swing.text.html.ListView;
import java.util.Arrays;

/**
 * Created by Geashaw on 10-2-2015.
 */
public class Controller {

    @FXML
    private ComboBox<String> cmbOre;
    @FXML
    private ComboBox<String> cmbArea;
    @FXML
    private ComboBox<String> cmbMethod;
    @FXML
    private Button btnStart;

    public void startController() {

        // Create a new String array for the ore names.
        String[] cmbOreItems = new String[]{"Clay", "Tin", "Copper", "Iron"};
        // Add the above strings to the cmbHide ComboBox.
        cmbOre.getItems().addAll(cmbOreItems);

        // Create a new String array for the ore names.
        String[] cmbLocationItems = new String[]{"South-east Varrock", "South-west Varrock", "Al Kharid", "Rimmington"};
        // Add the above strings to the cmbHide ComboBox.
        cmbArea.getItems().addAll(cmbLocationItems);

        // Create a new String array for the ore names.
        String[] cmbMethodItems = new String[]{"Powermine", "Bank"};
        // Add the above strings to the cmbHide ComboBox.
        cmbMethod.getItems().addAll(cmbMethodItems);

        // Set the action event.
        btnStart.setOnAction(event -> {

            // Check if the ComboBox for the ore is not empty.
            if (!cmbOre.getSelectionModel().isEmpty()) {

                String ore = cmbOre.getSelectionModel().getSelectedItem();
                LazyAIOMiner.selectedOre = ore;

                Integer[] clay = {7481, 7483, 13456, 13457, 13458};
                Integer[] tin = {7484, 7486, 13447, 13448, 13449};
                Integer[] copper = {7478, 7479, 7480, 13450, 13451, 13452, 13708};
                Integer[] iron = {7487, 7488, 7489, 13444, 13445, 13446, 13710, 13711, };
                Integer[] silver = {13716, 13717};
                Integer[] coal = {13714};
                Integer[] gold = {7490, 7492, 13707, 13715};
                Integer[] mithril = {13718, 13719};
                Integer[] adamantite = {14168, 13720};

                if (ore == "Clay") {
                    LazyAIOMiner.oreName = "Clay";
                    LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(clay));
                    LazyAIOMiner.preferedTile = new Coordinate(3180, 3371, 0);
                } else if (ore == "Tin") {
                    LazyAIOMiner.oreName = "Tin ore";
                    LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(tin));
                    LazyAIOMiner.preferedTile = new Coordinate(3282, 3363, 0);
                } else if (ore == "Copper") {
                    LazyAIOMiner.oreName = "Copper ore";
                    LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(copper));
                    LazyAIOMiner.preferedTile = new Coordinate(3289, 3362, 0);
                } else if (ore == "Iron") {
                    LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(iron));
                    LazyAIOMiner.oreName = "Iron ore";
                    LazyAIOMiner.preferedTile = new Coordinate(3286, 3368, 0);
                }
            }

            // Check if the ComboBox for the area is not empty.
            if (!cmbArea.getSelectionModel().isEmpty()) {

                String area = cmbArea.getSelectionModel().getSelectedItem();

                final Area VarrockEastMineArea = new Area.Rectangular(new Coordinate(3294,3355,0),new Coordinate(3276,3372,0));
                final Area VarrockEastBankArea = new Area.Rectangular(new Coordinate(3249,3415,0),new Coordinate(3257,3425,0));

                final Area VarrockSouthMineArea = new Area.Rectangular(new Coordinate(3170,3362,0),new Coordinate(3185,3380,0));
                final Area VarrockSouthBankArea = new Area.Rectangular(new Coordinate(3179,3432,0),new Coordinate(3190,3447,0));

                final Area AlKharidMineArea = new Area.Rectangular(new Coordinate(3286,3275,0),new Coordinate(3311,3320,0));
                final Area AlKharidBankArea = new Area.Rectangular(new Coordinate(3264,3160,0),new Coordinate(3272,3174,0));

                final Area RimmingtonMineArea = new Area.Circular(new Coordinate(2977,3238,0),14);
                final Area RimmingtonBankArea = new Area.Rectangular(new Coordinate(3008,3350,0),new Coordinate(3018,3359,0));

                if (area == "South-east Varrock") {
                    LazyAIOMiner.mineArea = VarrockEastMineArea;
                    LazyAIOMiner.bankArea = VarrockEastBankArea;
                } else if (area == "South-west Varrock") {
                    LazyAIOMiner.mineArea = VarrockSouthMineArea;
                    LazyAIOMiner.bankArea = VarrockSouthBankArea;
                } else if (area == "Al Kharid") {
                    LazyAIOMiner.mineArea = AlKharidMineArea;
                    LazyAIOMiner.bankArea = AlKharidBankArea;
                } else if (area == "Rimmington") {
                    LazyAIOMiner.mineArea = RimmingtonMineArea;
                    LazyAIOMiner.bankArea = RimmingtonBankArea;
                }
            }

            if (!cmbMethod.getSelectionModel().isEmpty()) {
                String method = cmbMethod.getSelectionModel().getSelectedItem();
                if (method == "Powermine") {
                    LazyAIOMiner.powermine = true;
                } else {
                    LazyAIOMiner.powermine = false;
                }
            }

            LazyAIOMiner.guiOpen = false;
            // Close the gui.
            Loader.guiStage.close();
        });
    }
}
