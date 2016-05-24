package controller.FXController;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import model.ComparisonFile;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class EditorTextAreaFXController {
    @FXML
    private TextArea editorTextArea;

    public void setContent(ComparisonFile comparisonFile) {
        if(comparisonFile != null) editorTextArea.textProperty().bind(comparisonFile.textProperty());
    }

    public void setEditable(boolean value){
        editorTextArea.setEditable(value);
    }

    public boolean isEditable(){
        return editorTextArea.isEditable();
    }

    public Node getContentNode() {
        return editorTextArea;
    }
}
