package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/15/2016.
 * 편집 창의 액션을 관리하는 컨트롤러
 */
public class EditorPaneFXController implements Initializable{
    private enum ViewMode{ EDIT, COMPARE }
    private File source;
    private ViewMode viewMode;
    private TextArea editorTextArea;
    private ListView compareListView;
    private CompareModeDisabler compareModeDisabler;

    @FXML
    private Pane rootPane;
    @FXML
    private ToggleButton editButton;
    @FXML
    private Button saveButton;

    @FXML
    private void handleLoadAction(ActionEvent event){
        if(source != null){
            // 이미 다른 파일을 편집중이면 저장할 지 물어봐야 함.
            Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save this file?");
            alert.setHeaderText("Save this file?");
            alert.setContentText("If you load other file, you will lose all change about this file.\n Click \'Yes\', if you want to save your changes");
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> save); // TODO save method
        }
        loadFromFile();
    }

    @FXML
    private void handleEditAction(ActionEvent event){
        // TODO 파일이 없으면 edit 불가능 -> 사용자에게 알려줘야 함

        // Compare 모드일 때는 compareModeDisabler에게 해제를 요청함
        if(viewMode == ViewMode.COMPARE) {
            // TODO 모델에서 비교 후 저장된 텍스트 내용을 가져와야 함.
            switchEditorTextArea(null);
            editorTextArea.setEditable(true);
        }else{ // Edit 모드일 때
            if(source != null) {
                boolean editable = editorTextArea.isEditable();
                // read only <-> edit
                editorTextArea.setEditable(!editable);
                editButton.setSelected(!editable);
            }
        }
    }

    @FXML
    private void handleSaveAction(ActionEvent event){
        if(viewMode == ViewMode.EDIT) saveTargetFromContent(source, editorTextArea.getText());
        else{
            // TODO 모델에서 비교 후 저장된 텍스트 내용을 가져와야 함.
        }
    }

    public boolean isFileContained(){
        if(source != null) return true;
        else return false;
    }

    public String getTextAreaContent(){
        if(viewMode == ViewMode.EDIT) return editorTextArea.getText();
        else return null;
    }

    public ListView getCompareListView(){
        return compareListView;
    }

    public void switchEditorTextArea(String content){
        viewMode = ViewMode.EDIT;
        if(compareModeDisabler != null) compareModeDisabler.disableCompareMode();
        editorTextArea.setText(content);
        setContentPane(editorTextArea);
    }

    public void switchCompareListView(ObservableList content){
        viewMode = ViewMode.COMPARE;
        compareListView.setItems(content);
        editButton.setSelected(false);
        setContentPane(compareListView);
    }

    public void setCompareModeDisabler(CompareModeDisabler compareModeDisabler){ this.compareModeDisabler = compareModeDisabler; }

    private void setDisableEditModeButtons(boolean value){
        editButton.setDisable(value);
        saveButton.setDisable(value);
    }

    // 파일을 불러오는 과정
    private void loadFromFile(){
        File selectedFile = showFileChooser();
        String content = readContentFromFile(selectedFile);
        if(content != null) {
            switchEditorTextArea(content);
            source = selectedFile;
            editorTextArea.setEditable(false);
            setDisableEditModeButtons(false);
            editButton.setSelected(false);
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
    private String readContentFromFile(File source){
        // 선택한 파일 불러서 파일 내용 가져오기
        String content = null;
        try {
            content =  readFile(source);
        }catch(IOException ioe){
            Alert fileLoadErrorAlert = new Alert(Alert.AlertType.ERROR);
            fileLoadErrorAlert.setTitle("File load failed");
            fileLoadErrorAlert.setHeaderText(null);
            fileLoadErrorAlert.setContentText("Selected file is not loaded. Check if it exists and try again.");
            fileLoadErrorAlert.show();
        }finally {
            return content;
        }
    }
    private void saveTargetFromContent(File target, String content){
        try{
            writeFile(target, content);
        }catch(IOException ioe){
            Alert fileLoadErrorAlert = new Alert(Alert.AlertType.ERROR);
            fileLoadErrorAlert.setTitle("File save failed");
            fileLoadErrorAlert.setHeaderText(null);
            fileLoadErrorAlert.setContentText("Selected file is not saved. Check if it saved normally or try again.");
            fileLoadErrorAlert.show();
        }
    }

    // 파일 입출력
    private String readFile(File source) throws IOException {
        StringBuffer content = null;
        try (BufferedReader reader = Files.newBufferedReader(source.toPath(), StandardCharsets.UTF_8)) {
            content = new StringBuffer();

            String line = null;
            while((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
            content.deleteCharAt(content.length()-1);
        }
        return content.toString();
    }
    private void writeFile(File target, String content) throws IOException {
        try(BufferedWriter writer = Files.newBufferedWriter(target.toPath(),
                StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            writer.write(content);
        }
    }

    // 파일 내용을 보여주는 창 교체
    private void setContentPane(Node node) {
        List children = rootPane.getChildren();
        if(children.size() > 1) children.set(1, node);
        else children.add(1, node);
    }
    private void addContentPaneProperty(Node node){
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 40.0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            editorTextArea = FXMLLoader.load(getClass().getResource("/fxml/EditorTextArea.fxml"));
            compareListView = FXMLLoader.load(getClass().getResource("/fxml/CompareListView.fxml"));
            addContentPaneProperty(editorTextArea);
            addContentPaneProperty(compareListView);
            switchEditorTextArea(null);
        }catch(IOException ioe){
            // TODO Pane 불러오기 실패에 대한 알림
        }
    }
}
