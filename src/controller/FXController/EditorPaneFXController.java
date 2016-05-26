package controller.FXController;

import controller.CompareModeDisabler;
import controller.ViewMode;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import model.editorModel.EditorModelInterface;
import model.fileIO.ComparisonFileReader;
import model.fileIO.ComparisonFileWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.fileIO.file.ObservableComparisonFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/15/2016.
 * 편집 창의 액션을 관리하는 컨트롤러
 */
public class EditorPaneFXController implements Initializable, EditorPaneControllerInterface{
    private EditorModelInterface model;
    private ViewMode viewMode;
    private EditorTextAreaControllerInterface editorTextAreaFXController;
    private CompResultListViewControllerInterface compResultListViewFXController;
    private CompareModeDisabler compareModeDisabler;

    @FXML
    private Pane rootPane;
    @FXML
    private Label filePathLabel;
    @FXML
    private ToggleButton editButton;
    @FXML
    private Button saveButton;

    @FXML
    private void handleLoadAction(ActionEvent event){
        if(model.fileReadOnlyProperty().get() != null){ // TODO 모델에게 파일이 존재하는 지 쿼리해야 함
            // 이미 다른 파일을 편집중이면 저장할 지 물어봐야 함.
            Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save this file?");
            alert.setHeaderText("Save this file?");
            alert.setContentText("If you load other file, you will lose all change about this file.\n Click \'Yes\', if you want to save your changes");
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> saveToFile());
        }
        loadFromFile();
    }

    @FXML
    private void handleEditAction(ActionEvent event){
        // Compare 모드일 때는 compareModeDisabler에게 해제를 요청함
        if(viewMode == ViewMode.COMPARE) {
            switchEditorTextArea();
            setEditable(true);
        }else{ // Edit 모드일 때
            if(model.fileReadOnlyProperty().get() != null) {
                boolean editable = editorTextAreaFXController.isEditable();
                // read only <-> edit
                editorTextAreaFXController.setEditable(!editable);
                editButton.setSelected(!editable);
            }
        }
    }

    @FXML
    private void handleSaveAction(ActionEvent event){
        saveToFile();
    }

    @Override
    public void setModel(EditorModelInterface model){
        this.model = model;
        model.fileReadOnlyProperty().addListener((observable, oldValue, newValue) -> {
            filePathLabel.setText(newValue.getPath());
        });
        editorTextAreaFXController.setModel(this.model.getObservableTextModel());
        compResultListViewFXController.setModel(this.model.getObservableListModel());
    }

    @Override
    public int getSelectedIndex() {
        return compResultListViewFXController.getSelectedIndex();
    }

    @Override
    public void scrollTo(int value){
        compResultListViewFXController.scrollTo(value);
    }

    @Override
    public void switchCompareListView(){
        viewMode = ViewMode.COMPARE;
        setEditable(false);
        setContentNode(compResultListViewFXController.getContentNode());
    }

    @Override
    public void setCompareModeDisabler(CompareModeDisabler compareModeDisabler){ this.compareModeDisabler = compareModeDisabler; }

    @Override
    public Node getContentNode() {
        return rootPane;
    }

    private void switchEditorTextArea(){
        viewMode = ViewMode.EDIT;
        if(compareModeDisabler != null) compareModeDisabler.disableCompareMode();
        setContentNode(editorTextAreaFXController.getContentNode());
    }

    private void setDisableEditModeButtons(boolean value){
        editButton.setDisable(value);
        saveButton.setDisable(value);
    }

    private void saveToFile(){
        try {
            model.save();
        }catch(IOException ioe){
            Alert fileLoadErrorAlert = new Alert(Alert.AlertType.ERROR);
            fileLoadErrorAlert.setTitle("File save failed");
            fileLoadErrorAlert.setHeaderText(null);
            fileLoadErrorAlert.setContentText("Selected file is not saved. Check if it saved normally or try again.");
            fileLoadErrorAlert.show();
        }
    }

    // 파일을 불러오는 과정
    private void loadFromFile(){
        File selectedFile = showFileChooser();
        if(selectedFile == null) return;
        try {
            model.load(selectedFile);
            setEditable(false);
            setDisableEditModeButtons(false);
            switchEditorTextArea();
        }catch (IOException ioe){
            Alert fileLoadErrorAlert = new Alert(Alert.AlertType.ERROR);
            fileLoadErrorAlert.setTitle("File load failed");
            fileLoadErrorAlert.setHeaderText(null);
            fileLoadErrorAlert.setContentText("Selected file is not loaded. Check if it exists and try again.");
            fileLoadErrorAlert.show();
        }
    }

    private void setEditable(boolean value){
        editButton.setSelected(value);
        editorTextAreaFXController.setEditable(value);
    }

    private File showFileChooser(){
        // FileChooser로 불러올 파일 선택
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text file", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        return selectedFile;
    }

    // 파일 내용을 보여주는 창 교체
    private void setContentNode(Node node) {
        List children = rootPane.getChildren();
        if(children.size() > 1) children.set(1, node);
        else children.add(1, node);
    }
    private void addContentNodeProperty(Node node){
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 40.0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader editorTextAreaFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/EditorTextArea.fxml"));
            editorTextAreaFXMLLoader.load();
            editorTextAreaFXController = editorTextAreaFXMLLoader.getController();
            FXMLLoader compareListViewFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/CompareListView.fxml"));
            compareListViewFXMLLoader.load();
            compResultListViewFXController = compareListViewFXMLLoader.getController();
            addContentNodeProperty(editorTextAreaFXController.getContentNode());
            addContentNodeProperty(compResultListViewFXController.getContentNode());
            switchEditorTextArea();
        }catch(IOException ioe){
            // TODO Pane 불러오기 실패에 대한 알림
        }
    }
}
