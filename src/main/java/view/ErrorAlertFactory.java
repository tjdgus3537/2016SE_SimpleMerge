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

    /**
     * 뷰를 불러오다 실패했을 때, 보여주는 에러 메세지를 생헝합니다.
     * @return 뷰 불러오기를 실패했을 때, 보여주는 Alert 객체
     */
    public static Alert newViewLoadErrorAlert(){
        Alert viewLoadAlert = newErrorAlert();
        viewLoadAlert.setHeaderText("View load failed");
        viewLoadAlert.setContentText("View is not loaded. Please Restart the program.\n" +
                "If this error occurs several time, please report bug to wraithkim@cau.ac.kr");
        return viewLoadAlert;
    }

    /**
     * 파일 저장에 실패했을 때, 보여주는 에러 메세지를 생성합니다.
     * @return 파일 저장에 실패했을 때, 보여주는 Alert 객체
     */
    public static Alert newFileSaveErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File save failed");
        fileLoadErrorAlert.setContentText("Selected file is not saved. Check if it saved normally or try again.");
        return fileLoadErrorAlert;
    }

    /**
     * 파일 인코딩 감지에 실패했을 때, 보여주는 에러 메세지를 생성합니다.
     * @return 파일 인코딩 감지에 실패했을 때, 보여주는 Alert 객체
     */
    public static Alert newFileEncodingErrorAlert(){
        Alert fileEncodingErrorAlert = newErrorAlert();
        fileEncodingErrorAlert.setHeaderText("Can't detect file encoding.");
        fileEncodingErrorAlert.setContentText("The character set of this file is unknown. Please convert this file to utf-8 encoding.");
        return fileEncodingErrorAlert;
    }

    /**
     * 파일 불러오기에 실패했을 때, 보여주는 에러 메세지를 생성합니다.
     * @return 파일 불러오기에 실패했을 때, 보여주는 Alert 객체
     */
    public static Alert newFileLoadErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("File load failed");
        fileLoadErrorAlert.setContentText("Selected file is not loaded. Check if it exists and try again.");
        return fileLoadErrorAlert;
    }
}
