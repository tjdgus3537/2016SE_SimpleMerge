package controller.mainWindow.editorPane.viewMode;

import controller.mainWindow.editorPane.EditorPaneControllerInterface;

/**
 * Created by Donghwan on 2016-11-15.
 *
 * 편집창의 비교 모드
 */
public class CompMode implements EditorPaneViewMode {
    private EditorPaneControllerInterface editorPaneControllerInterface;

    public CompMode(EditorPaneControllerInterface editorPaneControllerInterface) {
        this.editorPaneControllerInterface = editorPaneControllerInterface;
    }

    @Override
    public void handleLoadAction() {
        editorPaneControllerInterface.notifyDisableCompMode();
        editorPaneControllerInterface.loadFromFile();
    }

    @Override
    public void handleEditAction() {
        editorPaneControllerInterface.notifyDisableCompMode();
        editorPaneControllerInterface.switchEditorTextArea();
        editorPaneControllerInterface.setTextEditable(true);
    }
}
