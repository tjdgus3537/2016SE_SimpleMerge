package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 2016-11-24.
 *
 * 한글로 된 오류창과 확인창을 생성하는 객체
 */
public class KoreanAlertFactory implements AlertFactory{
    private volatile static KoreanAlertFactory instance;

    public static KoreanAlertFactory getInstance(){
        if(instance == null){
            synchronized (EnglishAlertFactory.class){
                if(instance == null){
                    instance = new KoreanAlertFactory();
                }
            }
        }
        return instance;
    }

    private KoreanAlertFactory(){}

    private Alert newErrorAlert(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("오류");
        return errorAlert;
    }

    private Alert newConfirmationAlert(){
        Alert confirmationAlert =  new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("확인");
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

    // TODO 일본어로 번역
    private Alert newViewLoadErrorAlert(){
        Alert viewLoadAlert = newErrorAlert();
        viewLoadAlert.setHeaderText("뷰 불러오기 실패");
        viewLoadAlert.setContentText("뷰를 불러오는데 실패했습니다. 프로그램을 재시작해주세요.\n" +
                "만약 이 오류가 여러번 발생한다면, wraithkim@cau.ac.kr로 버그 신고를 해주세요.");
        return viewLoadAlert;
    }

    private Alert newFileSaveErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("파일 저장하기 실패");
        fileLoadErrorAlert.setContentText("선택한 파일이 저장되지 않았습니다. 정상적으로 저장되었는지 확인해보시고 다시 시도해주세요.");
        return fileLoadErrorAlert;
    }

    private Alert newFileEncodingErrorAlert(){
        Alert fileEncodingErrorAlert = newErrorAlert();
        fileEncodingErrorAlert.setHeaderText("파일 인코딩을 찾을 수 없습니다.");
        fileEncodingErrorAlert.setContentText("이 파일의 문자 인코딩을 찾을 수 없습니다. 이 파일을 utf-8 인코딩으로 바꿔주세요.");
        return fileEncodingErrorAlert;
    }

    private Alert newFileLoadErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("파일 불러오기 실패");
        fileLoadErrorAlert.setContentText("선택된 파일을 불러오는데 실패했습니다. 해당 파일이 존재하는지 확인하고 다시 시도해주세요.");
        return fileLoadErrorAlert;
    }

    private Alert newSaveEditedFileConfirmationAlert(){
        Alert saveEditedFileAlert = newConfirmationAlert();
        saveEditedFileAlert.setHeaderText("이 파일을 저장하시겠습니까?");
        saveEditedFileAlert.setContentText("만약 다른 파일을 불러온다면, 이 파일의 모든 변경점이 지워집니다.\n 만약 변경점을 저장하고 싶으면 \'예\'를 클릭하세요.");
        return saveEditedFileAlert;
    }
}
