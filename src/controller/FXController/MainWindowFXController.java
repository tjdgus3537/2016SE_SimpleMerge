package controller.FXController;

import controller.CompareModeDisabler;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SplitPane;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collector;

/**
 * Created by Donghwan on 5/14/2016.
 * 메인 윈도우의 액션을 관리하는 컨트롤러
 */
public class MainWindowFXController implements Initializable, CompareModeDisabler {
    private EditorPaneFXController leftPaneController;
    private EditorPaneFXController rightPaneController;
    private Diff diff;
    ObservableList<Block> left;
    ObservableList<Block> right;

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
        // TODO fix: diff 직접 사용중
        diff = new Diff();
        PairBlocks pairBlocks = diff.compare(leftPaneController.getComparisonFile().getContentToString(), rightPaneController.getComparisonFile().getContentToString());
        left = FXCollections.observableArrayList(pairBlocks.getLeft());
        right = FXCollections.observableArrayList(pairBlocks.getRight());
        right.addListener((ListChangeListener<Block>) c -> {
            rightPaneController.getComparisonFile().textProperty().setValue(left.stream().map(item->item.getContent()).collect(Collector.of(()->new StringBuffer(), StringBuffer::append, StringBuffer::append, StringBuffer::toString)));
        });
        leftPaneController.getCompareListView().setItems(left);
        rightPaneController.getCompareListView().setItems(right);
        compareViewScrollBar.setMax(pairBlocks.getLeft().size()); // comp 결과 길이가 들어가야 함
    }

    // TODO Comp 모드에서는 undo 불가?
    @FXML
    private void handleCopyToRightButtonAction(ActionEvent event){
        int blockNum = leftPaneController.getCompareListView().getSelectionModel().getSelectedIndex();
        if(left.get(blockNum).getState() == State.UNCHANGED) return;

        //우측의 blockNum번 block을 좌측에 추가하고, 좌측의 blockNum + 1번째 block을 삭제.
        right.add(blockNum, left.get(blockNum));
        right.remove(blockNum + 1);
    }

    @FXML
    private void handleCopyToLeftButtonAction(ActionEvent event){
        DiffCommandInterface diff = new DiffCommand();
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
