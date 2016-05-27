package model.fileIO.file;

import javafx.beans.property.ReadOnlyObjectProperty;

import java.io.File;

/**
 * Created by Donghwan on 5/26/2016.
 */
public interface FilePropertyProvider {
    /**
     * 파일을 가지고 있는 ReadOnlyObjectProperty를 반환함
     * @return 파일을 가지고 있는 ReadOnlyObjectProperty
     */
    ReadOnlyObjectProperty<File> fileReadOnlyProperty();
}
