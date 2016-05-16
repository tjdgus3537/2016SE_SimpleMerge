package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/14/2016.
 * 메인 윈도우의 액션을 관리하는 컨트롤러
 */
public class MainWindowFXController implements Initializable{
    @FXML
    private SplitPane editorSplitPane;

    @FXML
    protected void handleCompareButtonAction(ActionEvent event){

    }

    @FXML
    protected void handleCopyToRightButtonAction(ActionEvent event){

    }

    @FXML
    protected void handelCopyToLeftButtonAction(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            AnchorPane leftEditorPane = FXMLLoader.load(getClass().getResource("/fxml/EditorPane.fxml"));
            AnchorPane rightEditorPane = FXMLLoader.load(getClass().getResource("/fxml/EditorPane.fxml"));
            editorSplitPane.getItems().add(leftEditorPane);
            editorSplitPane.getItems().add(rightEditorPane);
            editorSplitPane.setDividerPositions(0.5);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
