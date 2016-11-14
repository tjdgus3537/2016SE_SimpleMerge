package controller.mainWindow.editorPane.viewMode;

/**
 * Created by Donghwan on 2016-11-15.
 *
 * 편집창의 화면 상태를 나타내는 객체의 인터페이스
 */
public interface EditorPaneViewMode {
    /**
     * 편집창에서 불러오기 버튼을 누를 경우, 실행된다.
     */
    void handleLoadAction();

    /**
     * 편집창에서 편집 버튼을 누를 경우, 실행된다.
     */
    void handleEditAction();
}

