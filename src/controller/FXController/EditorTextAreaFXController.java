package controller.FXController;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import model.ComparisonFile;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class EditorTextAreaFXController implements EditorTextAreaControllerInterface{
    @FXML
    private TextArea editorTextArea;

    @Override
    public void setContent(StringProperty stringProperty) {
        if(stringProperty != null) editorTextArea.textProperty().bindBidirectional(stringProperty);
    }

    @Override
    public void setEditable(boolean value){
        editorTextArea.setEditable(value);
    }

    @Override
    public boolean isEditable(){
        return editorTextArea.isEditable();
    }

    @Override
    public Node getContentNode() {
        return editorTextArea;
    }
}
