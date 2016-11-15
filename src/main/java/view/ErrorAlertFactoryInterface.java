package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 2016-11-15.
 *
 * 경고창을 생성하는 추상 팩토리 객체의 인터페이스
 */
public interface ErrorAlertFactoryInterface {
    /**
     * 뷰를 불러오다 실패했을 때, 보여주는 에러 메세지를 생헝합니다.
     * @return 뷰 불러오기를 실패했을 때, 보여주는 Alert 객체
     */
    Alert newViewLoadErrorAlert();

    /**
     * 파일 저장에 실패했을 때, 보여주는 에러 메세지를 생성합니다.
     * @return 파일 저장에 실패했을 때, 보여주는 Alert 객체
     */
    Alert newFileSaveErrorAlert();

    /**
     * 파일 인코딩 감지에 실패했을 때, 보여주는 에러 메세지를 생성합니다.
     * @return 파일 인코딩 감지에 실패했을 때, 보여주는 Alert 객체
     */
    Alert newFileEncodingErrorAlert();

    /**
     * 파일 불러오기에 실패했을 때, 보여주는 에러 메세지를 생성합니다.
     * @return 파일 불러오기에 실패했을 때, 보여주는 Alert 객체
     */
    Alert newFileLoadErrorAlert();
}
