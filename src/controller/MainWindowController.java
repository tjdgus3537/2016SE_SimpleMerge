package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by Donghwan on 5/14/2016.
 * 메인 윈도우의 액션을 관리하는 컨트롤러
 */
public class MainWindowController {
    @FXML
    private Button compareButton;
    @FXML
    private Button copyToRightButton;
    @FXML
    private Button copyToLeftButton;

    @FXML
    protected void handelCompareButtonAction(ActionEvent event){
        System.out.println("comp");
    }

    @FXML
    protected void handleCopyToRightButtonAction(ActionEvent event){
        System.out.println("copytoright");
    }

    @FXML
    protected void handelCopyToLeftButtonAction(ActionEvent event){
        System.out.println("copytoleft");
    }
}
