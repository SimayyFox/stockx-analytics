import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainDisplay extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("StockX Analytical System");
        primaryStage.getIcons().add(new Image("https://cdn6.aptoide.com/imgs/7/6/8/768f51268a3b4435dc9004d7586d81f6_icon.png"));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((a) -> Platform.exit());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
