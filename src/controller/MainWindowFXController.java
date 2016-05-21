package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/14/2016.
 * 메인 윈도우의 액션을 관리하는 컨트롤러
 */
public class MainWindowFXController implements Initializable, CompareModeDisabler{
    private EditorPaneFXController leftPaneController;
    private EditorPaneFXController rightPaneController;

    @FXML
    private Button compareButton;
    @FXML
    private Button copyToRightButton;
    @FXML
    private Button copyToLeftButton;
    @FXML
    private SplitPane editorSplitPane;
    @FXML
    private ScrollBar compareViewScrollBar;

    @FXML
    private void handleCompareButtonAction(ActionEvent event){
        // 양 쪽 pane에 파일이 존재하지 않으면 비교 불가
        if(!leftPaneController.isFileContained() || !rightPaneController.isFileContained()) {
            return;
        }
        setDisableCompareModeNodes(false);
        leftPaneController.switchCompareListView(null);
        rightPaneController.switchCompareListView(null);
        // TODO compare 결과를 넣어줘야함.
        compareViewScrollBar.setMax(1); // comp 결과 길이가 들어가야 함
    }

    @FXML
    private void handleCopyToRightButtonAction(ActionEvent event){

    }

    @FXML
    private void handleCopyToLeftButtonAction(ActionEvent event){

    }

    private void setDisableCompareModeNodes(boolean value){
        copyToRightButton.setDisable(value);
        copyToLeftButton.setDisable(value);
        compareViewScrollBar.setDisable(value);
        compareButton.setDisable(!value);
    }

    @Override
    public void disableCompareMode() {
        setDisableCompareModeNodes(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/EditorPane.fxml"));
            AnchorPane leftEditorPane = fxmlLoader.load();
            leftPaneController = fxmlLoader.getController();
            leftPaneController.setCompareModeDisabler(this);
            fxmlLoader.setRoot(null);
            fxmlLoader.setController(null);
            fxmlLoader.setLocation(getClass().getResource("/fxml/EditorPane.fxml"));
            AnchorPane rightEditorPane = fxmlLoader.load();
            rightPaneController = fxmlLoader.getController();
            rightPaneController.setCompareModeDisabler(this);
            List items = editorSplitPane.getItems();
            items.add(leftEditorPane);
            items.add(rightEditorPane);
            editorSplitPane.setDividerPositions(0.5);
            compareViewScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
                leftPaneController.getCompareListView().scrollTo(newValue.intValue());
                rightPaneController.getCompareListView().scrollTo(newValue.intValue());
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
