package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 6/2/2016.
 *
 * 확인 창을 생성하는 추상 팩토리 객체
 */
public class ConfirmationAlertFactory implements ConfirmationAlertFactoryInterface{

    private volatile static ConfirmationAlertFactory singleton;

    public static ConfirmationAlertFactory getInstance(){
        if(singleton == null){
            synchronized (ConfirmationAlertFactory.class){
                if(singleton == null){
                    singleton = new ConfirmationAlertFactory();
                }
            }
        }
        return singleton;
    }

    private ConfirmationAlertFactory(){}

    private Alert newConfirmationAlert(){
        Alert confirmationAlert =  new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        return confirmationAlert;
    }

    @Override
    public Alert newSaveEditedFileConfirmationAlert(){
        Alert saveEditedFileAlert = newConfirmationAlert();
        saveEditedFileAlert.setHeaderText("Save this file?");
        saveEditedFileAlert.setContentText("If you load other file, you will lose all change about this file.\n Click \'Yes\' to save your changes");
        return saveEditedFileAlert;
    }
}
