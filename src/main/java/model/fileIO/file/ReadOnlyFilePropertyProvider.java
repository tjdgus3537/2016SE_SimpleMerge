package model.fileIO.file;

import javafx.beans.property.ReadOnlyObjectProperty;

import java.io.File;

/**
 * Created by Donghwan on 5/26/2016.
 *
 * File 객체의 읽기 전용의 property 객체를 제공하는 클래스의 인터페이스
 */
public interface ReadOnlyFilePropertyProvider {
    /**
     * 파일을 가지고 있는 ReadOnlyObjectProperty를 반환함
     * @return 파일을 가지고 있는 ReadOnlyObjectProperty
     */
    ReadOnlyObjectProperty<File> fileReadOnlyProperty();
}
