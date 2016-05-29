package model.fileIO.file;

import javafx.beans.property.StringProperty;

/**
 * Created by Donghwan on 5/26/2016.
 *
 * 텍스트 내용의 Property 객체를 제공하는 클래스의 인터페이스
 */
public interface TextPropertyProvider {
    /**
     * 문자열 속성을 반환한다.
     * @return 문자열 속성
     */
    StringProperty textProperty();
}
