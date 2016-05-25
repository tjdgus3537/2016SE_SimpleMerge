package controller.FXController;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;

/**
 * Created by Donghwan on 5/25/2016.
 */
public interface EditorTextAreaControllerInterface extends ContentNodeProvider {
    void setModel(ObservableTextModelInterface model);
    void setEditable(boolean value);
    boolean isEditable();
}
