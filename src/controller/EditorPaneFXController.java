package controller;

import controller.fileIO.ComparisonFileReader;
import controller.fileIO.ComparisonFileWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.ComparisonFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/15/2016.
 * 편집 창의 액션을 관리하는 컨트롤러
 */
public class EditorPaneFXController implements Initializable{
    private ComparisonFile comparisonFile;
    private ViewMode viewMode;
    private EditorTextAreaFXController editorTextAreaFXController;
    private CompareListViewFXController compareListViewFXController;
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
        if(comparisonFile != null){
            // 이미 다른 파일을 편집중이면 저장할 지 물어봐야 함.
            Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save this file?");
            alert.setHeaderText("Save this file?");
            alert.setContentText("If you load other file, you will lose all change about this file.\n Click \'Yes\', if you want to save your changes");
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> saveToFile()); // TODO save method
        }
        loadFromFile();
    }

    @FXML
    private void handleEditAction(ActionEvent event){
        // TODO 파일이 없으면 edit 불가능 -> 사용자에게 알려줘야 함

        // Compare 모드일 때는 compareModeDisabler에게 해제를 요청함
        if(viewMode == ViewMode.COMPARE) {
            editorTextAreaFXController.setContent(comparisonFile);
            switchEditorTextArea();
            editorTextAreaFXController.setEditable(true);
        }else{ // Edit 모드일 때
            if(comparisonFile != null) {
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

    public boolean isFileContained(){
        if(comparisonFile != null) return true;
        else return false;
    }

    public void switchEditorTextArea(){
        viewMode = ViewMode.EDIT;
        if(compareModeDisabler != null) compareModeDisabler.disableCompareMode();
        editorTextAreaFXController.setContent(comparisonFile);
        setContentNode(editorTextAreaFXController.getContentNode());
    }

    public void switchCompareListView(){
        viewMode = ViewMode.COMPARE;
        editButton.setSelected(false);
        setContentNode(compareListViewFXController.getContentNode());
    }

    public void setCompareModeDisabler(CompareModeDisabler compareModeDisabler){ this.compareModeDisabler = compareModeDisabler; }

    private void setDisableEditModeButtons(boolean value){
        editButton.setDisable(value);
        saveButton.setDisable(value);
    }

    private void saveToFile(){
        try {
            ComparisonFileWriter.writeComparisonFile(comparisonFile);
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
        try {
            ComparisonFile loadedFile = ComparisonFileReader.readComparisonFile(selectedFile);
            if (loadedFile != null) {
                comparisonFile = loadedFile;
                switchEditorTextArea();
                editorTextAreaFXController.setEditable(false);
                setDisableEditModeButtons(false);
                editButton.setSelected(false); // set read-only
                filePathLabel.setText(selectedFile.getPath().toString());
            }
        }catch (IOException ioe){
            Alert fileLoadErrorAlert = new Alert(Alert.AlertType.ERROR);
            fileLoadErrorAlert.setTitle("File load failed");
            fileLoadErrorAlert.setHeaderText(null);
            fileLoadErrorAlert.setContentText("Selected file is not loaded. Check if it exists and try again.");
            fileLoadErrorAlert.show();
        }
    }

    private File showFileChooser(){
        // FileChooser로 불러올 파일 선택
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
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
            compareListViewFXController = compareListViewFXMLLoader.getController();
            addContentNodeProperty(editorTextAreaFXController.getContentNode());
            addContentNodeProperty(compareListViewFXController.getContentNode());
            switchEditorTextArea();
        }catch(IOException ioe){
            // TODO Pane 불러오기 실패에 대한 알림
        }
    }
}
