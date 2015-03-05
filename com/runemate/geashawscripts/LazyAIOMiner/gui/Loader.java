package com.runemate.geashawscripts.LazyAIOMiner.gui;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.framework.AbstractScript;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Created by Ozzy on 10-2-2015.
 */
public class Loader extends Stage {

    public static Stage guiStage;

    // Constructor to call our start method if you instantiate a loader object.
    public Loader() {
        try {
            start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Method to be called to initiate the GUI, load the fxml etc.
    public void start(Stage stage) throws Exception {
        InputStream fxmlInput = LazyAIOMiner.class.getResourceAsStream("/com/runemate/geashawscripts/LazyAIOMiner/gui/LazyAIOMinerGUI.fxml");
        if (fxmlInput != null) {
            // FXML has been found successfully.
            FXMLLoader fxmlLoader = new FXMLLoader();
            // Assign the controller which will control ui elements.
            fxmlLoader.setController(new Controller());
            Parent guiRoot = fxmlLoader.load(fxmlInput);
            // Set your GUI panels properties.
            Scene guiScene = new Scene(guiRoot);
            stage.setTitle("Lazy AIO Miner 0.8");
            stage.setScene(guiScene);
            guiStage = stage;
            Controller controller = fxmlLoader.getController();
            controller.startController();
            // Might want to call a method in your controller here to set up initial gui fields.
            stage.setOnCloseRequest(event -> {
                if (Environment.getScript() != null) {
                    AbstractScript script = Environment.getScript();
                    if (script.isRunning()) {
                        script.stop();
                    }
                }
                stage.close();
            });
            stage.show();
        } else {
            System.out.println("Failed to locate fxml");
        }
    }


}
