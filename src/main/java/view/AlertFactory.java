package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 2016-11-23.
 *
 * 오류창과 확인창을 생성하는 객체의 인터페이스
 */
public interface AlertFactory {
    /**
     * 오류창을 생성한다.
     * @param type 생성할 오류창의 종류
     * @return 위의 type에 대응되는 오류창
     */
    Alert newErrorAlert(String type);

    /**
     * 확인창을 생성한다.
     * @param type 생성할 확인창의 종류
     * @return 위의 type에 대응되는 확인창
     */
    Alert newConfirmationAlert(String type);
}
