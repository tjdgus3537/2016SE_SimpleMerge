package model.fileIO.file;

import javafx.beans.property.ObjectProperty;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by Donghwan on 5/27/2016.
 *
 * 비교하는 파일에 대해서 저장하는 클래스의 인터페이스
 */
public interface ComparisonTargetInterface extends ObservableBlocksProvider, TextPropertyProvider{

    /**
     * 해당 FIle 객체를 저장한다
     * @param source 저장할 File 객체
     */
    void setSource(File source);

    /**
     * 해당 문자열로 파일 내용을 저장한다.
     * @param content 저장할 파일 내용
     */
    void setContent(String content);

    /**
     * 해당 문자 인코딩 형식으로 설정한다.
     * @param charset 설정할 문자 인코딩
     */
    void setEncoding(Charset charset);

    /**
     * 저장하고 있는 File 객체를 반환한다.
     * @return 저장하고 있는 File 객체
     */
    File getSource();

    /**
     * 저장하고 있는 파일 내용을 반환한다.
     * @return 저장하고 있는 파일 내용
     */
    String getContent();

    /**
     * 설정된 문자 인코딩 형식을 반환한다.
     * @return 설정된 문자 인코딩
     */
    Charset getEncoding();

    /**
     * File 객체를 담고 있는 Property 객체를 반환한다.
     * @return File 객체를 담고 있는 Property 객체
     */
    ObjectProperty<File> fileProperty();
}
