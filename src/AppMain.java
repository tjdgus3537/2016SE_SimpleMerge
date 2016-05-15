/**
 * Created by Donghwan on 5/14/2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("SimpleMerge");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}