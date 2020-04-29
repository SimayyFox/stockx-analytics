import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainScene {
    @FXML
    private TextField querybox;

    @FXML
    public void onEnter(ActionEvent ae) throws IOException {
        BufferedReader searchreader = URLProcessor.getSearchReader(querybox.getText());
        String[] skuname = JSONProcessor.getSKU(searchreader);
        String sku = skuname[0];
        String name = skuname[1];
        BufferedReader pagereader = URLProcessor.getReader(sku);
        SizePriceMap spm = JSONProcessor.initialize(pagereader);
        changeScene(ae, spm, name);
    }

    /**
     * When this method is called, the scene gets changed to the homepage.
     * @param event the event that triggers this method
     *
     */
    public Stage changeScene(ActionEvent event, SizePriceMap spm, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        AnalyticsScene analyticsController = new AnalyticsScene();
        analyticsController.setSpm(spm);
        analyticsController.setName(name);
        loader.setController(analyticsController);
        loader.setLocation(getClass().getResource("/analyticsScene.fxml"));
        Parent analyticsParent = loader.load();
        Scene analyticsScene = new Scene(analyticsParent);

        //Get current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(analyticsScene);
        stage.setTitle("SKU Bar Chart - " + name);
        return stage;
    }
}
