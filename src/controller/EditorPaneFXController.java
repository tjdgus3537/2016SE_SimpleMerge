package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Created by Donghwan on 5/15/2016.
 * 편집 창의 액션을 관리하는 컨트롤러
 */
public class EditorPaneFXController {
    private File source;

    @FXML
    private Pane rootPane;

    @FXML
    private TextArea contentTextArea;

    @FXML
    protected void handleEditAction(ActionEvent event){
        contentTextArea.setEditable(true);
    }

    @FXML
    protected void handleSaveAction(ActionEvent event){
        saveTargetFromContent(source, contentTextArea.getText());
    }

    @FXML
    protected void handleLoadAction(ActionEvent event){
        File selectedFile = showFileChooser();
        String content = loadTargetFromFile(selectedFile);
        if(content != null) {
            setTargetToContentTextArea(content);
            source = selectedFile;
            contentTextArea.setEditable(false);
        }
    }

    private File showFileChooser(){
        // FileChooser로 불러올 파일 선택
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }

    private String loadTargetFromFile(File source){
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

    private void setTargetToContentTextArea(String content){
        contentTextArea.setText(content);
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
}
