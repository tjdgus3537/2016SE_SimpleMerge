package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 2016-11-23.
 */
public class EnglishAlertFactory implements AlertFactory{
    private volatile static EnglishAlertFactory instance;

    public static EnglishAlertFactory getInstance(){
        if(instance == null){
            synchronized (EnglishAlertFactory.class){
                if(instance == null){
                    instance = new EnglishAlertFactory();
                }
            }
        }
        return instance;
    }

    private EnglishAlertFactory(){}

    private Alert newErrorAlert(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        return errorAlert;
    }

    private Alert newConfirmationAlert(){
        Alert confirmationAlert =  new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        return confirmationAlert;
    }

    @Override
    public Alert newErrorAlert(String type) {
        switch(type){
            case "ViewLoad":
                return newViewLoadErrorAlert();
            case "FileSave":
                return newFileSaveErrorAlert();
            case "FileEncoding":
                return newFileEncodingErrorAlert();
            case "FileLoad":
                return newFileLoadErrorAlert();
            default:
                return null;
        }
    }

    @Override
    public Alert newConfirmationAlert(String type) {
        switch(type){
            case "SaveEditedFile":
                return newSaveEditedFileConfirmationAlert();
            default: return null;
        }
    }

    private Alert newViewLoadErrorAlert(){
        Alert viewLoadAlert = newErrorAlert();
        viewLoadAlert.setHeaderText("View load failed");
        viewLoadAlert.setContentText("View is not loaded. Please Restart the program.\n" +
                "If this error occurs several time, please report bug to wraithkim@cau.ac.kr");
        return viewLoadAlert;
    }

    private Alert newFileSaveErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File save failed");
        fileLoadErrorAlert.setContentText("Selected file is not saved. Check if it saved normally or try again.");
        return fileLoadErrorAlert;
    }

    private Alert newFileEncodingErrorAlert(){
        Alert fileEncodingErrorAlert = newErrorAlert();
        fileEncodingErrorAlert.setHeaderText("Can't detect file encoding.");
        fileEncodingErrorAlert.setContentText("The character set of this file is unknown. Please convert this file to utf-8 encoding.");
        return fileEncodingErrorAlert;
    }

    private Alert newFileLoadErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File load failed");
        fileLoadErrorAlert.setContentText("Selected file is not loaded. Check if it exists and try again.");
        return fileLoadErrorAlert;
    }

    private Alert newSaveEditedFileConfirmationAlert(){
        Alert saveEditedFileAlert = newConfirmationAlert();
        saveEditedFileAlert.setHeaderText("Save this file?");
        saveEditedFileAlert.setContentText("If you load other file, you will lose all change about this file.\n Click \'Yes\' to save your changes");
        return saveEditedFileAlert;
    }
}
