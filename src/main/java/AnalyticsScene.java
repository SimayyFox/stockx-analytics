import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class AnalyticsScene {
    @FXML
    private BarChart priceChartSales;
    @FXML
    private BarChart priceChartAsks;
    @FXML
    private BarChart priceChartBids;
    @FXML
    private CategoryAxis cAxisSales;
    @FXML
    private CategoryAxis cAxisAsks;
    @FXML
    private CategoryAxis cAxisBids;
    @FXML
    private NumberAxis nAxisSales;
    @FXML
    private NumberAxis nAxisAsks;
    @FXML
    private NumberAxis nAxisBids;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab salesTab;
    @FXML
    private Tab asksTab;
    @FXML
    private Tab bidsTab;

    private SizePriceMap spmSales;
    private SizePriceMap spmAsks;
    private SizePriceMap spmBids;
    private String name;

    /**
     * Initializes the charts.
     */
    public void initialize() {
        TreeSet<Double> sizeset = spmSales.getSizeSet();
        initializeSalesChart(sizeset);
        initializeAsksChart(sizeset);
        initializeBidsChart(sizeset);
    }

    public void back(ActionEvent event) {
        changeScene(event, "/mainScene.fxml");
    }

    /** This method changes the scene to the referenced fxml file.
     *
     * @param event the event that triggers the change of the scene
     * @param path the path to the FXML file
     */
    public void changeScene(ActionEvent event, String path) {
        Parent parent = loadFxml(path);
        if (parent == null) {
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load the provided FXML file.
     * @param path the path to the FXML file
     */
    public Parent loadFxml(String path) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loading failed");
            alert.setContentText("Loading failed");
            alert.setHeaderText(null);
            alert.showAndWait();
            return null;
        }
        return parent;
    }

    /**
     * Initializes the sales chart.
     * @param sizeset - the set with all the sizes
     */
    public void initializeSalesChart(TreeSet<Double> sizeset) {
        priceChartSales.setTitle(name);
        cAxisSales.setLabel("Size");
        nAxisSales.setLabel("Price");

        ArrayList<XYChart.Series> serielist = getSerieList(sizeset, spmSales);

        for (int k = 0; k < serielist.size(); k++) {
            priceChartSales.getData().add(k, serielist.get(k));
        }

        priceChartSales.setLegendVisible(false);
    }

    /**
     * Initializes the asks chart.
     * @param sizeset - the set with all the sizes
     */
    public void initializeAsksChart(TreeSet<Double> sizeset) {
        priceChartAsks.setTitle(name);
        cAxisAsks.setLabel("Size");
        nAxisAsks.setLabel("Price");

        ArrayList<XYChart.Series> serielist = getSerieList(sizeset, spmAsks);

        for (int k = 0; k < serielist.size(); k++) {
            priceChartAsks.getData().add(k, serielist.get(k));
        }

        priceChartAsks.setLegendVisible(false);
    }

    /**
     * Initializes the bids chart.
     * @param sizeset - the set with all the sizes
     */
    public void initializeBidsChart(TreeSet<Double> sizeset) {
        priceChartBids.setTitle(name);
        cAxisBids.setLabel("Size");
        nAxisBids.setLabel("Price");

        ArrayList<XYChart.Series> serielist = getSerieList(sizeset, spmBids);

        for (int k = 0; k < serielist.size(); k++) {
            priceChartBids.getData().add(k, serielist.get(k));
        }

        priceChartBids.setLegendVisible(false);
    }

    /**
     * Gets the ArrayList with all the series, which contain the actual price - size data.
     * @param sizeset the set with all the sizes
     * @param spm - the SizePriceMap with all the sizes and prices
     * @return an arraylist which contains the series
     */
    public ArrayList<XYChart.Series> getSerieList(TreeSet<Double> sizeset, SizePriceMap spm) {
        ArrayList<XYChart.Series> serielist = new ArrayList<>();

        for (int i = 0; i < sizeset.size(); i++) {
            serielist.add(new XYChart.Series());
        }

        int count = 0;
        int average = 0;

        for (Double size : sizeset) {
            serielist.get(count).getData().add(new XYChart.Data(size.toString(), spm.mean(size)));
            count++;
        }

        return serielist;
    }

    public SizePriceMap getSpmSales() {
        return spmSales;
    }

    public void setSpmSales(SizePriceMap spm) {
        this.spmSales = spm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SizePriceMap getSpmAsks() {
        return spmAsks;
    }

    public void setSpmAsks(SizePriceMap spm2) {
        this.spmAsks = spm2;
    }

    public SizePriceMap getSpmBids() {
        return spmBids;
    }

    public void setSpmBids(SizePriceMap spm3) {
        this.spmBids = spm3;
    }
}