package controller.FXController;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class EditorTextAreaFXController implements EditorTextAreaControllerInterface{
    private ObservableTextModelInterface model;
    @FXML
    private TextArea editorTextArea;

    @Override
    public void setModel(ObservableTextModelInterface model) {
        this.model = model;
        editorTextArea.textProperty().bindBidirectional(model.textProperty());
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
