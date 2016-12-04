package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 2016-11-24.
 *
 * 일본어로 된 오류창과 확인창을 생성하는 객체
 */
public class JapaneseAlertFactory implements AlertFactory{
    private volatile static JapaneseAlertFactory instance;

    public static JapaneseAlertFactory getInstance(){
        if(instance == null){
            synchronized (JapaneseAlertFactory.class){
                if(instance == null){
                    instance = new JapaneseAlertFactory();
                }
            }
        }
        return instance;
    }

    private JapaneseAlertFactory(){}

    private Alert newErrorAlert(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("エラー");
        return errorAlert;
    }

    private Alert newConfirmationAlert(){
        Alert confirmationAlert =  new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("確認");
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
        viewLoadAlert.setHeaderText("ヴュー読み込み失敗");
        viewLoadAlert.setContentText("ヴューの読み込みに失敗しました。 プログラムを再起動してください。\n" +
                "もしこのエラーが一度ならず発生する場合、, 是非バグレポートをwraithkim@cau.ac.krまでに送ってください。");
        return viewLoadAlert;
    }

    private Alert newFileSaveErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("ファイル保存失敗");
        fileLoadErrorAlert.setContentText("選択されたファイルの保存に失敗しました。問題なく保存されているか確認したあと、やり直してください。");
        return fileLoadErrorAlert;
    }

    private Alert newFileEncodingErrorAlert(){
        Alert fileEncodingErrorAlert = newErrorAlert();
        fileEncodingErrorAlert.setHeaderText("ファイルの文字コード");
        fileEncodingErrorAlert.setContentText("このファイルの文字コードが見つかりません。 このファイルをutf-8文字コードに変換してください。");
        return fileEncodingErrorAlert;
    }

    private Alert newFileLoadErrorAlert(){
        Alert fileLoadErrorAlert = newErrorAlert();
        fileLoadErrorAlert.setHeaderText("ファイル読み込み失敗");
        fileLoadErrorAlert.setContentText("選択されたファイルの読み込みに失敗しました。ファイルが存在するかどうかを確認してからやり直してください。");
        return fileLoadErrorAlert;
    }

    private Alert newSaveEditedFileConfirmationAlert(){
        Alert saveEditedFileAlert = newConfirmationAlert();
        saveEditedFileAlert.setHeaderText("このファイルを保存しますか？");
        saveEditedFileAlert.setContentText("もし他のファイルを読み込むと、このファイルのすべての変更事項が消されます。\n もし変更事項を保存したい場合、 'はい'を押してください。");

        return saveEditedFileAlert;
    }
}
