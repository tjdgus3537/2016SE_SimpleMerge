package model.editorModel.contentNodeModel;

import javafx.beans.property.StringProperty;
import model.fileIO.file.TextPropertyProvider;

/**
 * Created by Donghwan on 5/25/2016.
 */
public class ObservableTextModel implements ObservableTextModelInterface {
    TextPropertyProvider textPropertyProvider;

    public ObservableTextModel(TextPropertyProvider textPropertyProvider) {
        this.textPropertyProvider = textPropertyProvider;
    }

    @Override
    public StringProperty textProperty() {
        return textPropertyProvider.textProperty();
    }
}
