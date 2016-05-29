import controller.mainWindow.MainWindowControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MainModel;
import model.MainModelInterface;

/**
 * Created by Donghwan on 5/14/2016.
 *
 * 프로그램의 시작 지점
 */

public class AppMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainModelInterface mainModel = new MainModel();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = fxmlLoader.load();
        MainWindowControllerInterface controller = fxmlLoader.getController();
        controller.setModel(mainModel);
        Scene scene = new Scene(root);
        primaryStage.setTitle("SimpleMerge - Team 6");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
