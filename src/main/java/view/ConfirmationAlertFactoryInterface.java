package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 2016-11-15.
 *
 * 확인 창을 생성하는 추상 팩토리 객체의 인터페이스
 */
public interface ConfirmationAlertFactoryInterface {
    /**
     * 편집한 파일을 저장할 것이냐고 묻는 확인 메세지를 생성합니다.
     * @return 편집한 파일을 저장할 것인지 묻는 Alert 객체
     */
    Alert newSaveEditedFileConfirmationAlert();
}
