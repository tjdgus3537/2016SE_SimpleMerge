package model.editorModel.contentNodeModel;

import javafx.beans.property.StringProperty;
import model.fileIO.file.TextPropertyProvider;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * Observable 인터페이스를 상속받은 텍스트를 제공하는 모델
 */
public class ObservableTextModel implements ObservableTextModelInterface {
    private final TextPropertyProvider textPropertyProvider;

    public ObservableTextModel(TextPropertyProvider textPropertyProvider) {
        this.textPropertyProvider = textPropertyProvider;
    }

    @Override
    public StringProperty textProperty() {
        return textPropertyProvider.textProperty();
    }
}
