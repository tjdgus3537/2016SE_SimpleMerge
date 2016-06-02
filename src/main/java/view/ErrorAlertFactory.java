package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 6/2/2016.
 *
 * 경고창을 생성하는 팩토리 클래스
 */
public class ErrorAlertFactory {

    private ErrorAlertFactory(){}

    private static Alert newErrorAlert(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        return errorAlert;
    }

    public static Alert newViewLoadErrorAlert(){
        Alert viewLoadAlert = newErrorAlert();
        viewLoadAlert.setHeaderText("View load failed");
        viewLoadAlert.setContentText("View is not loaded. Please Restart the program.\n" +
                "If this error occurs several time, please report bug to wraithkim@cau.ac.kr");
        return viewLoadAlert;
    }

    public static Alert newFileSaveErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File save failed");
        fileLoadErrorAlert.setContentText("Selected file is not saved. Check if it saved normally or try again.");
        return fileLoadErrorAlert;
    }

    public static Alert newFileEncodingErrorAlert(){
        Alert fileEncodingErrorAlert = newErrorAlert();
        fileEncodingErrorAlert.setHeaderText("Can't detect file encoding.");
        fileEncodingErrorAlert.setContentText("The character set of this file is unknown. Please convert this file to utf-8 encoding.");
        return fileEncodingErrorAlert;
    }

    public static Alert newFileLoadErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File load failed");
        fileLoadErrorAlert.setContentText("Selected file is not loaded. Check if it exists and try again.");
        return fileLoadErrorAlert;
    }
}
