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
    public static Alert newSaveEditedFileConfirmationAlert(){
        Alert saveEditedFileAlert = newConfirmationAlert();
        saveEditedFileAlert.setHeaderText("Save this file?");
        saveEditedFileAlert.setContentText("If you load other file, you will lose all change about this file.\n Click \'Yes\' to save your changes");
        return saveEditedFileAlert;
    }
}
