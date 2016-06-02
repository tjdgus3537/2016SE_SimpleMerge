package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 6/2/2016.
 *
 * 확인 창을 생성하는 팩토리 클래스
 */
public class ConfirmationAlertFactory {
    private ConfirmationAlertFactory(){}

    private static Alert newConfirmationAlert(){
        Alert confirmationAlert =  new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        return confirmationAlert;
    }

    /**
     * 편집한 파일을 저장할 것이냐고 묻는 확인 메세지를 생성합니다.
     * @return 편집한 파일을 저장할 것인지 묻는 Alert 객체
     */
    public static Alert newSaveEditedFileConfirmationAlert(){
        Alert saveEditedFileAlert = newConfirmationAlert();
        saveEditedFileAlert.setHeaderText("Save this file?");
        saveEditedFileAlert.setContentText("If you load other file, you will lose all change about this file.\n Click \'Yes\' to save your changes");
        return saveEditedFileAlert;
    }
}
