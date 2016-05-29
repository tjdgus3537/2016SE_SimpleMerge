package controller.mainWindow.editorPane.listView;

import controller.ContentNodeProvider;
import controller.mainWindow.editorPane.SingleSelectionListViewControllerInterface;
import model.editorModel.contentNodeModel.ObservableCompResultInterface;

/**
 * Created by Donghwan on 5/25/2016.
 * 비교 결과를 보여주는 뷰의 컨트롤러 인터페이스
 */
public interface CompResultsViewControllerInterface extends ContentNodeProvider, SingleSelectionListViewControllerInterface {
    /**
     * 해당 컨트롤러가 참조할 모델을 설정한다.
     * @param model ObservableList 형태의 비교 결과를 제공해주는 모델
     */
    void setModel(ObservableCompResultInterface model);
}
