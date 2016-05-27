package controller.mainWindow.editorPane.textArea;

/**
 * Created by Donghwan on 5/27/2016.
 *
 * 편집 가능한 내용을 출력하는 뷰의 컨트롤러
 */
public interface EditStateManageable {
    /**
     * 편집 가능한 상태를 바꾼다
     * @param editable true면 편집 가능, false면 읽기 전용
     */
    void setEditable(boolean editable);

    /**
     * 편집 가능한 지 반환한다.
     * @return 편집 가능하면 true, 읽기 전용이면 false다.
     */
    boolean isEditable();
}
