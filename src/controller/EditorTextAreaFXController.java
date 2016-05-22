package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class EditorTextAreaFXController implements ContentHolder {
    private String model;
    @FXML
    private TextArea editorTextArea;

    public void setModel(String model){
        this.model = model;
        editorTextArea.setText(this.model);
    }

    @Override
    public String getContentString() {
        return model;
    }
}
