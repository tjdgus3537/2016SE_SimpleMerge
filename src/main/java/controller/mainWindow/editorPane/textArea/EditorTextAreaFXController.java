package controller.mainWindow.editorPane.textArea;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;

/**
 * Created by Donghwan on 5/22/2016.
 *
 * 편집 창에서 파일의 내용을 편집할 수 있는 텍스트 에이리어의 컨트롤러
 */
public class EditorTextAreaFXController implements EditorTextAreaControllerInterface {
    private ObservableTextModelInterface model;
    @FXML
    private TextArea editorTextArea;

    @Override
    public void setModel(ObservableTextModelInterface model) {
        this.model = model;
        editorTextArea.textProperty().bindBidirectional(model.textProperty());
    }

    @Override
    public void setEditable(boolean editable){
        editorTextArea.setEditable(editable);
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
