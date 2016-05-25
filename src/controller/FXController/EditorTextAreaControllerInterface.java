package controller.FXController;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;

/**
 * Created by Donghwan on 5/25/2016.
 */
public interface EditorTextAreaControllerInterface extends ContentNodeProvider {
    void setContent(StringProperty stringProperty);
    void setEditable(boolean value);
    boolean isEditable();
    @Override
    Node getContentNode();
}
