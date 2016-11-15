package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 6/2/2016.
 *
 * 경고창을 생성하는 추상 팩토리 객체
 */
public class ErrorAlertFactory implements ErrorAlertFactoryInterface{

    private volatile static ErrorAlertFactory singleton;

    public static ErrorAlertFactory getInstance(){
        if(singleton == null){
            synchronized (ErrorAlertFactory.class){
                if(singleton == null){
                    singleton = new ErrorAlertFactory();
                }
            }
        }
        return singleton;
    }

    private ErrorAlertFactory(){}

    private Alert newErrorAlert(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        return errorAlert;
    }

    @Override
    public Alert newViewLoadErrorAlert(){
        Alert viewLoadAlert = newErrorAlert();
        viewLoadAlert.setHeaderText("View load failed");
        viewLoadAlert.setContentText("View is not loaded. Please Restart the program.\n" +
                "If this error occurs several time, please report bug to wraithkim@cau.ac.kr");
        return viewLoadAlert;
    }

    @Override
    public Alert newFileSaveErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File save failed");
        fileLoadErrorAlert.setContentText("Selected file is not saved. Check if it saved normally or try again.");
        return fileLoadErrorAlert;
    }

    @Override
    public Alert newFileEncodingErrorAlert(){
        Alert fileEncodingErrorAlert = newErrorAlert();
        fileEncodingErrorAlert.setHeaderText("Can't detect file encoding.");
        fileEncodingErrorAlert.setContentText("The character set of this file is unknown. Please convert this file to utf-8 encoding.");
        return fileEncodingErrorAlert;
    }

    @Override
    public Alert newFileLoadErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File load failed");
        fileLoadErrorAlert.setContentText("Selected file is not loaded. Check if it exists and try again.");
        return fileLoadErrorAlert;
    }
}
