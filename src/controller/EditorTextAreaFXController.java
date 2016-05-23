package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import model.ComparisonFile;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class EditorTextAreaFXController implements ContentNodeProvider{
    @FXML
    private TextArea editorTextArea;

    public void setContent(ComparisonFile comparisonFile) {
        if(comparisonFile != null) editorTextArea.textProperty().bind(comparisonFile.getTextAreaProperty());
    }

    public void setEditable(boolean value){
        editorTextArea.setEditable(value);
    }

    public boolean isEditable(){
        return editorTextArea.isEditable();
    }

    @Override
    public Node getContentNode() {
        return editorTextArea;
    }
}
