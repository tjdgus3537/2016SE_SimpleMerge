package controller.mainWindow.editorPane;

import controller.CompModeDisableReceiver;
import controller.ContentNodeProvider;
import model.editorModel.EditorModelInterface;

/**
 * Created by Donghwan on 5/26/2016.
 *
 * 편집 창의 컨트롤러 인터페이스
 */
public interface EditorPaneControllerInterface extends ContentNodeProvider, SingleSelectionListViewControllerInterface{
    /**
     * 해당 컨트롤러가 참조할 모델을 설정한다.
     * @param model 컨트롤러가 참조할 모델
     */
    void setModel(EditorModelInterface model);

    /**
     * 비교 결과를 보여주는 뷰로 편집 창을 전환한다.
     */
    void switchCompResultsView();

    /**
     * 비교 모드를 끄는 명령을 받는 객체를 등록한다.
     * @param compModeDisableReceiver 비교 모드를 끄는 명령을 받는 객체
     */
    void setCompModeDisableReceiver(CompModeDisableReceiver compModeDisableReceiver);

    /**
     * 텍스트가 편집 가능한지를 반환한다
     * @return 편집 가능하면 true
     */
    boolean isTextEditable();

    /**
     * 매개변수가 true인 경우, 텍스트를 편집 가능하게 바꾸고, false 인 경우, 읽기 전용으로 바꾼다.
     * @param editable 편집 가능하면 true
     */
    void setTextEditable(boolean editable);

    /**
     * 파일을 선택하는 창을 띄우고 선택된 파일을 불러온다.
     */
    void loadFromFile();

    /**
     * 파일의 텍스트를 보여주는 뷰로 편집 창을 전환한다.
     */
    void switchEditorTextArea();

    /**
     * 뷰가 비교 모드를 보여주는 것을 비활성화한다.
     */
    void disableCompMode();
}
