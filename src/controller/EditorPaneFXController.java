package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Donghwan on 5/15/2016.
 * 편집 창의 액션을 관리하는 컨트롤러
 */
public class EditorPaneFXController {
    @FXML
    private Pane rootPane;

    @FXML
    public void handleLoadAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
        }
    }
}
