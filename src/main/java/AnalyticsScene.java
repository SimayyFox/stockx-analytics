import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.TreeSet;

public class AnalyticsScene {
    @FXML
    private BarChart pricechart;

    @FXML
    private CategoryAxis cAxis;

    @FXML
    private NumberAxis nAxis;

    private SizePriceMap spm;
    private String name;

    public void initialize() {
        TreeSet<Double> sizeset = spm.getSizeSet();

        pricechart.setTitle(name);
        cAxis.setLabel("Size");
        nAxis.setLabel("Price");

        ArrayList<XYChart.Series> serielist = new ArrayList<>();

        for (int i = 0; i < sizeset.size(); i++) {
            serielist.add(new XYChart.Series());
        }

        int count = 0;

        for (Double size : sizeset) {
            serielist.get(count).getData().add(new XYChart.Data(size.toString(), spm.mean(size)));
            count++;
        }

        for (int k = 0; k < serielist.size(); k++) {
            pricechart.getData().add(k, serielist.get(k));
        }

        pricechart.setLegendVisible(false);
    }

    public SizePriceMap getSpm() {
        return spm;
    }

    public void setSpm(SizePriceMap spm) {
        this.spm = spm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


//import javafx.fxml.FXML;
//        import javafx.scene.Scene;
//        import javafx.scene.chart.*;
//        import javafx.stage.Stage;
//
//        import java.util.ArrayList;
//        import java.util.TreeSet;
//
//public class AnalyticsScene {
//    @FXML
//    private Chart pricechart;
//
//    @FXML
//    private CategoryAxis xaxis;
//
//    @FXML
//    private CategoryAxis yaxis;
//
//    public static void initialize(SizePriceMap spm, Stage stage, String name) {
//        TreeSet<Double> sizeset = spm.getSizeSet();
//        stage.setTitle("SKU Bar Chart - " + name);
//
//        final CategoryAxis xAxis = new CategoryAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
//        bc.setTitle(name);
//        xAxis.setLabel("Size");
//        yAxis.setLabel("Price");
//
//        ArrayList<XYChart.Series> serielist = new ArrayList<>();
//
//        for (int i = 0; i < sizeset.size(); i++) {
//            serielist.add(new XYChart.Series());
//        }
//
//        int count = 0;
//
//        for (Double size : sizeset) {
//            serielist.get(count).getData().add(new XYChart.Data(size.toString(), spm.mean(size)));
//            count++;
//        }
//
//        for (int k = 0; k < serielist.size(); k++) {
//            bc.getData().add(k, serielist.get(k));
//        }
//
//        bc.setCategoryGap(1.0);
//        bc.setBarGap(0);
//        bc.setMaxWidth(200.0);
//        //pricechart = bc;
//        Scene scene  = new Scene(bc, 800, 600);
//        stage.setScene(scene);
//        stage.show();
//    }
//}
