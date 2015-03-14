package com.runemate.geashawscripts.LazyAIOMiner.gui;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.Arrays;

/**
 * Created by Geashaw on 10-2-2015.
 */
public class Controller {

    @FXML
    private CheckBox checkboxPowermine;
    @FXML
    private CheckBox checkboxDropGems;
    @FXML
    private CheckBox checkboxNoMove;
    @FXML
    private CheckBox checkboxMineOneDropOne;
    @FXML
    private ListView<String> listLocation;
    @FXML
    private ListView<String> listOre;
    @FXML
    private Button buttonStart;

    @FXML
    private ObservableList<String> listLocationData = FXCollections.observableArrayList(
            "North-east Ardougne", "South-east Varrock", "South-west Varrock", "Al Kharid", "Rimmington", "Lumbridge East", "Lumbridge West"
    );
    @FXML
    private ObservableList<String> listOreData = FXCollections.observableArrayList(
            "Clay", "Tin ore", "Copper ore", "Iron ore", "Coal"
    );
    @FXML
    private ObservableList<String> listArdougneData = FXCollections.observableArrayList(
            "Iron ore", "Coal"
    );
    @FXML
    private ObservableList<String> listSouthEastVarrockData = FXCollections.observableArrayList(
            "Tin ore", "Copper ore", "Iron ore"
    );
    @FXML
    private ObservableList<String> listSouthWestVarrockData = FXCollections.observableArrayList(
            "Clay", "Tin ore", "Copper ore", "Iron ore"
    );
    @FXML
    private ObservableList<String> listAlKharidData = FXCollections.observableArrayList(
            "Clay", "Tin ore", "Copper ore", "Iron ore", "Coal", "Silver ore", "Gold ore", "Mithril ore", "Adamantite ore"
    );
    @FXML
    private ObservableList<String> listRimmingtonData = FXCollections.observableArrayList(
            "Clay", "Tin ore", "Copper ore", "Iron ore", "Gold ore"
    );
    @FXML
    private ObservableList<String> listLumbridgeEastData = FXCollections.observableArrayList(
            "Tin ore", "Copper ore"
    );
    @FXML
    private ObservableList<String> listLumbridgeWestData = FXCollections.observableArrayList(
            "Coal", "Mithril ore", "Adamantite ore"
    );

    final Area VarrockEastMineArea = new Area.Rectangular(new Coordinate(3294, 3355, 0), new Coordinate(3276, 3372, 0));
    final Area VarrockEastBankArea = new Area.Rectangular(new Coordinate(3249, 3415, 0), new Coordinate(3257, 3425, 0));

    final Area VarrockSouthMineArea = new Area.Rectangular(new Coordinate(3170, 3362, 0), new Coordinate(3185, 3380, 0));
    final Area VarrockSouthBankArea = new Area.Rectangular(new Coordinate(3179, 3432, 0), new Coordinate(3190, 3447, 0));

    final Area AlKharidMineArea = new Area.Rectangular(new Coordinate(3286, 3275, 0), new Coordinate(3311, 3320, 0));
    final Area AlKharidBankArea = new Area.Rectangular(new Coordinate(3264, 3160, 0), new Coordinate(3272, 3174, 0));

    final Area RimmingtonMineArea = new Area.Circular(new Coordinate(2977, 3238, 0), 14);
    final Area RimmingtonBankArea = new Area.Rectangular(new Coordinate(3008, 3350, 0), new Coordinate(3018, 3359, 0));

    final Area ArdougneMineArea = new Area.Rectangular(new Coordinate(2686,3325,0),new Coordinate(2717,3340,0));
    final Area ArdougneBankArea = new Area.Rectangular(new Coordinate(2647,3278,0),new Coordinate(2659,3288,0));

    final Area LumbridgeEastMineArea = new Area.Rectangular(new Coordinate(3220,3142,0),new Coordinate(3231,3149,0));

    final Area LumbridgeWestMineArea = new Area.Rectangular(new Coordinate(3141,3142,0),new Coordinate(3150,3154,0));
    final Area LumbridgeWestBankArea = new Area.Rectangular(new Coordinate(3090,3239,0),new Coordinate(3097,3246,0));

    Integer[] clay = {7481, 7483, 13456, 13457, 13458};
    Integer[] tin = {7484, 7486, 13447, 13448, 13449, 14883, 14864, 14863};
    Integer[] copper = {7478, 7479, 7480, 13450, 13451, 13452, 13708, 14884, 14885, 1488};
    Integer[] iron = {7487, 7488, 7489, 13444, 13445, 13446, 13710, 13711};
    Integer[] silver = {13716, 13717};
    Integer[] coal = {13706, 13714, 14860, 14861, 14862};
    Integer[] gold = {7490, 7492, 13707, 13715};
    Integer[] mithril = {13718, 13719, 14890, 14948, 14949};
    Integer[] adamantite = {14168, 13720, 14887, 14889};

    public void initialize() {
        listLocation.setItems(listLocationData);
        listOre.setItems(listOreData);

        listLocation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (observable.getValue().equals("North-east Ardougne")) {
                    listOre.setItems(listArdougneData);
                } else if (observable.getValue().equals("South-east Varrock")) {
                    listOre.setItems(listSouthEastVarrockData);
                } else if (observable.getValue().equals("South-west Varrock")) {
                    listOre.setItems(listSouthWestVarrockData);
                } else if (observable.getValue().equals("Al Kharid")) {
                    listOre.setItems(listAlKharidData);
                } else if (observable.getValue().equals("Rimmington")) {
                    listOre.setItems(listRimmingtonData);
                } else if (observable.getValue().equals("Lumbridge East")) {
                    listOre.setItems(listLumbridgeEastData);
                } else if (observable.getValue().equals("Lumbridge West")) {
                    listOre.setItems(listLumbridgeWestData);
                }
            }
        });
    }

    public void startController() {

        // Set the action event.
        buttonStart.setOnAction(event -> {

            // Get location and ore.
            String location = listLocation.getSelectionModel().getSelectedItem().toString();
            String ore = listOre.getSelectionModel().getSelectedItem().toString();

            // Set location for debugging.
            LazyAIOMiner.location = location;
            LazyAIOMiner.oreName = listOre.getSelectionModel().getSelectedItem().toString();

            if (location.equals("North-east Ardougne")) {
                LazyAIOMiner.bankArea = ArdougneBankArea;
                LazyAIOMiner.mineArea = ArdougneMineArea;
            } else if (location.equals("South-east Varrock")) {
                LazyAIOMiner.bankArea = VarrockEastBankArea;
                LazyAIOMiner.mineArea = VarrockEastMineArea;
            } else if (location.equals("South-west Varrock")) {
                LazyAIOMiner.bankArea = VarrockSouthBankArea;
                LazyAIOMiner.mineArea = VarrockSouthMineArea;
            } else if (location.equals("Al Kharid")) {
                LazyAIOMiner.bankArea = AlKharidBankArea;
                LazyAIOMiner.mineArea = AlKharidMineArea;
            } else if (location.equals("Rimmington")) {
                LazyAIOMiner.bankArea = RimmingtonBankArea;
                LazyAIOMiner.mineArea = RimmingtonMineArea;
            } else if (location.equals("Lumbridge East")) {
                LazyAIOMiner.mineArea = LumbridgeEastMineArea;
            } else if (location.equals("Lumbridge West")) {
                LazyAIOMiner.mineArea = LumbridgeWestMineArea;
                LazyAIOMiner.bankArea = LumbridgeWestBankArea;
            }

            if (ore.equals("Clay")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(clay));
            } else if (ore.equals("Tin ore")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(tin));
            } else if (ore.equals("Copper ore")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(copper));
            } else if (ore.equals("Iron ore")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(iron));
            } else if (ore.equals("Silver ore")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(silver));
            } else if (ore.equals("Coal")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(coal));
            } else if (ore.equals("Gold ore")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(gold));
            } else if (ore.equals("Mithril ore")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(mithril));
            } else if (ore.equals("Adamantite ore")) {
                LazyAIOMiner.oreObjectIds.addAll(Arrays.asList(adamantite));
            }

            if (checkboxPowermine != null) {
                if (checkboxPowermine.isSelected()) {
                    LazyAIOMiner.powermine = true;
                } else {
                    LazyAIOMiner.powermine = false;
                }
            }

            if (checkboxDropGems != null) {
                if (checkboxDropGems.isSelected()) {
                    LazyAIOMiner.dropgems = true;
                } else {
                    LazyAIOMiner.dropgems = false;
                }
            }

            if (checkboxNoMove != null) {
                if (checkboxNoMove.isSelected()) {
                    LazyAIOMiner.noMove = true;
                } else {
                    LazyAIOMiner.noMove = false;
                }
            }

            LazyAIOMiner.guiOpen = false;
            // Close the gui.
            Loader.guiStage.close();

        });
    }
}
