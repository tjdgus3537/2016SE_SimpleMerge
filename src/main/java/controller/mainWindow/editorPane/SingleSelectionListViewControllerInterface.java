package controller.mainWindow.editorPane;

/**
 * Created by Donghwan on 5/27/2016.
 * 단일 선택 리스트 뷰의 컨트롤러 인터페이스
 */
public interface SingleSelectionListViewControllerInterface {
    /**
     * 뷰의 스크롤을 해당 위치로 이동한다.
     * @param index 뷰에서 스크롤 이동을 통해 보여줘야 할 기준 항목의 위치
     */
    void scrollTo(int index);

    /**
     * 단일 선택 모드에서 뷰에서 선택된 항목의 위치를 반환한다.
     * @return 선택된 항목의 위치를 반환
     */
    int getSelectedIndex();
}
