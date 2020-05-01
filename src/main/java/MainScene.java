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
import java.util.stream.Collectors;

public class MainScene {
    @FXML
    private TextField querybox;

    @FXML
    public void onEnter(ActionEvent ae) throws IOException {
        BufferedReader searchreader = URLProcessor.getSearchReader(querybox.getText());
        String[] skuname = JSONProcessor.getSKU(searchreader);
        String sku = skuname[0];
        String name = skuname[1];
        BufferedReader asksReader = URLProcessor.getReaderAsks(sku);
        BufferedReader bidsReader = URLProcessor.getReaderBids(sku);
        BufferedReader salesReader = URLProcessor.getReaderSales(sku);

        String asksString = asksReader.lines().collect(Collectors.joining());
        String bidsString = bidsReader.lines().collect(Collectors.joining());
        String salesString = salesReader.lines().collect(Collectors.joining());

        int totalsales = JSONProcessor.getTotal(salesString);
        int totalasks = JSONProcessor.getTotal(asksString);
        int totalbids = JSONProcessor.getTotal(bidsString);

        SizePriceMap spmasks = JSONProcessor.initialize(asksString);
        SizePriceMap spmbids = JSONProcessor.initialize(bidsString);
        SizePriceMap spmsales = JSONProcessor.initialize(salesString);

        changeScene(ae, spmasks, spmbids, spmsales, name, totalsales, totalasks, totalbids);
    }

    /**
     * When this method is called, the scene gets changed to the homepage.
     * @param event the event that triggers this method
     *
     */
    public Stage changeScene(ActionEvent event, SizePriceMap spmasks, SizePriceMap spmbids, SizePriceMap spmsales, String name,
                             int totalsales, int totalasks, int totalbids) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        AnalyticsScene analyticsController = new AnalyticsScene();
        analyticsController.setSpmSales(spmsales);
        analyticsController.setSpmAsks(spmasks);
        analyticsController.setSpmBids(spmbids);
        analyticsController.setName(name);
        analyticsController.setTotalasksint(totalasks);
        analyticsController.setTotalbidsint(totalbids);
        analyticsController.setTotalsalesint(totalsales);
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
