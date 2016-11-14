package controller.mainWindow.editorPane.viewMode;

import controller.mainWindow.editorPane.EditorPaneControllerInterface;

/**
 * Created by Donghwan on 2016-11-15.
 *
 * 편집창의 편집 모드
 */
public class EditMode implements EditorPaneViewMode {
    private EditorPaneControllerInterface editorPaneControllerInterface;

    public EditMode(EditorPaneControllerInterface editorPaneControllerInterface) {
        this.editorPaneControllerInterface = editorPaneControllerInterface;
    }

    @Override
    public void handleLoadAction() {
        editorPaneControllerInterface.loadFromFile();
    }

    @Override
    public void handleEditAction() {
        // 편집 모드에서 읽기 전용과 쓰기 가능 모드 사이에서 전환한다.
        boolean editable = editorPaneControllerInterface.isTextEditable();
        editorPaneControllerInterface.setTextEditable(!editable);
    }

}
