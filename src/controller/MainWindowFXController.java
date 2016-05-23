package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import model.Diff;

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
        leftPaneController.switchCompareListView();
        rightPaneController.switchCompareListView();
        Diff diff = new Diff();
        diff.compare(leftPaneController.getComparisonFile(), rightPaneController.getComparisonFile());
        compareViewScrollBar.setMax(leftPaneController.getComparisonFile().getContent().size()); // comp 결과 길이가 들어가야 함
    }

    // TODO Comp 모드에서는 undo 불가?
    @FXML
    private void handleCopyToRightButtonAction(ActionEvent event){
        Diff diff = new Diff();
        diff.copyToRight(leftPaneController.getComparisonFile(), rightPaneController.getComparisonFile(), leftPaneController.getCompareListView().getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void handleCopyToLeftButtonAction(ActionEvent event){
        Diff diff = new Diff();
        diff.copyToLeft(leftPaneController.getComparisonFile(), rightPaneController.getComparisonFile(), rightPaneController.getCompareListView().getSelectionModel().getSelectedIndex());
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
            FXMLLoader leftPaneFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/EditorPane.fxml"));
            leftPaneFXMLLoader.load();
            leftPaneController = leftPaneFXMLLoader.getController();
            leftPaneController.setCompareModeDisabler(this);
            FXMLLoader rightPaneFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/EditorPane.fxml"));
            rightPaneFXMLLoader.load();
            rightPaneController = rightPaneFXMLLoader.getController();
            rightPaneController.setCompareModeDisabler(this);
            ObservableList<Node> items = editorSplitPane.getItems();
            items.add(leftPaneController.getContentNode());
            items.add(rightPaneController.getContentNode());
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
