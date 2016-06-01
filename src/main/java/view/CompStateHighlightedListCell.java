package view;

import javafx.scene.control.ListCell;
import model.diff.block.Block;

/**
 * Created by Donghwan on 5/20/2016.
 *
 * 비교 결과의 각 항목의 상태에 따라 그 항목을 하이라이팅하는 셀
 */
public class CompStateHighlightedListCell extends ListCell<Block> {
    private static final String SELECTED_STYLE = "-fx-background-color: #4579D3; -fx-text-fill: white;";
    private static final String DEFAULT_STYLE = "-fx-background-color: white; -fx-text-fill: black;";
    @Override
    protected void updateItem(Block item, boolean empty) {
        super.updateItem(item, empty);
        setStyle(null);
        setText(null);

        if(empty || item == null) return;
        // 항목의 내용을 셀에 넣는다.
        setText(item.getContent());
        // 항목의 비교 상태에 따라 다음과 같은 하이라이팅 옵션을 적용한다.
        setStyle(item.getState().getStyle());
        boolean modifiable = item.getState().isModifiable();
        if(!modifiable){
            setStyle(DEFAULT_STYLE);
        }
        setDisable(!modifiable);
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);
        // 선택된 상태이면 정해진 스타일 옵션으로 하이라이트 한다.
        if(selected) setStyle(SELECTED_STYLE);
        else {
            // 선택 되지 않은 경우 항목에 따라 하이라이팅, 항목이 없으면 기본 스타일로 바꾼다.
            if(!isEmpty())setStyle(getItem().getState().getStyle());
        }
    }
}
