package controller.mainWindow;

import controller.mainWindow.editorPane.EditorPaneControllerInterface;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SplitPane;
import model.*;
import view.AlertFactory;
import view.EnglishAlertFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/14/2016.
 *
 * 메인 창의 컨트롤러
 */
public class MainWindowFXController implements Initializable, MainWindowControllerInterface {
    private EditorPaneControllerInterface leftPaneController;
    private EditorPaneControllerInterface rightPaneController;
    private MainModelInterface model;
    private AlertFactory alertFactory;

    @FXML
    private Button compareButton;
    @FXML
    private Button copyToRightButton;
    @FXML
    private Button copyToLeftButton;
    @FXML
    private Button undoButton;
    @FXML
    private SplitPane editorSplitPane;
    @FXML
    private ScrollBar compareViewScrollBar;

    @FXML
    private void handleCompareButtonAction(ActionEvent event){
        // 양 쪽 pane에 파일이 존재하지 않으면 비교 불가
        model.compare();
        setDisableCompareModeNodes(false);
        leftPaneController.switchCompResultsView();
        rightPaneController.switchCompResultsView();
        compareViewScrollBar.setMax(model.size()); // comp 결과 길이가 들어가야 함
    }

    @FXML
    private void handleCopyToRightButtonAction(ActionEvent event){
        model.copyToRight(leftPaneController.getSelectedIndex());
        leftPaneController.clearListSelection();
        rightPaneController.clearListSelection();
    }

    @FXML
    private void handleCopyToLeftButtonAction(ActionEvent event){
        model.copyToLeft(rightPaneController.getSelectedIndex());
        leftPaneController.clearListSelection();
        rightPaneController.clearListSelection();
    }

    @FXML
    private void handleUndoButtonAction(ActionEvent event){
        model.undo();
    }

    @Override
    public void setModel(MainModelInterface model){
        this.model = model;
        leftPaneController.setModel(model.getLeftEditorModel());
        rightPaneController.setModel(model.getRightEditorModel());
        model.getComparableProperty().addListener((observable, oldValue, newValue) -> {
            compareButton.setDisable(!newValue);
        });
    }

    private void setDisableCompareModeNodes(boolean disable){
        copyToRightButton.setDisable(disable);
        copyToLeftButton.setDisable(disable);
        compareViewScrollBar.setDisable(disable);
        undoButton.setDisable(disable);
        compareButton.setDisable(!disable);
    }

    @Override
    public void disableCompareMode() {
        // 모든 편집 창을 편집 상태로 이전함
        setDisableCompareModeNodes(true);
        leftPaneController.clearListSelection();
        leftPaneController.switchEditorTextArea();
        leftPaneController.setTextEditable(true);
        rightPaneController.clearListSelection();
        rightPaneController.switchEditorTextArea();
        rightPaneController.setTextEditable(true);
        model.disableCompMode();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        alertFactory = EnglishAlertFactory.getInstance();
        try{
            FXMLLoader leftPaneFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/EditorPane.fxml"));
            leftPaneFXMLLoader.load();
            leftPaneController = leftPaneFXMLLoader.getController();
            leftPaneController.setCompModeDisableReceiver(this);
            FXMLLoader rightPaneFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/EditorPane.fxml"));
            rightPaneFXMLLoader.load();
            rightPaneController = rightPaneFXMLLoader.getController();
            rightPaneController.setCompModeDisableReceiver(this);
            ObservableList<Node> items = editorSplitPane.getItems();
            items.add(leftPaneController.getContentNode());
            items.add(rightPaneController.getContentNode());
            editorSplitPane.setDividerPositions(0.5);
            compareViewScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.intValue() < model.size()) {
                    leftPaneController.scrollTo(newValue.intValue());
                    rightPaneController.scrollTo(newValue.intValue());
                }
            });
            compareButton.setDisable(true);
        }catch(IOException ioe){
            Alert viewLoadAlert = alertFactory.newErrorAlert("ViewLoad");
            viewLoadAlert.show();
        }
    }
}
