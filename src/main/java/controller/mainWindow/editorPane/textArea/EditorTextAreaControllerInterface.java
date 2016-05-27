package controller.mainWindow.editorPane.textArea;

import controller.ContentNodeProvider;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * 편집 창의 텍스트 에이리어의 컨트롤러 인터페이스
 */
public interface EditorTextAreaControllerInterface extends ContentNodeProvider, EditStateManageable {
    /**
     * 해당 컨트롤러가 참조할 모델을 설정한다.
     * @param model StringProperty 형태의 비교 결과를 제공해주는 모델
     */
    void setModel(ObservableTextModelInterface model);

}
