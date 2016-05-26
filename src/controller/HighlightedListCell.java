package controller;

import javafx.scene.control.ListCell;
import model.diff.block.Block;

/**
 * Created by Donghwan on 5/20/2016.
 * 리스트 뷰에서 비교 결과를 하이라이팅하는 셀
 */
public class HighlightedListCell extends ListCell<Block> {
    private static final String SELECTED_STYLE = "-fx-background-color: #4579D3; -fx-text-fill: white;";
    private static final String DEFAULT_STYLE = "-fx-background-color: white; -fx-text-fill: black;";
    @Override
    protected void updateItem(Block item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null) return;
        setText(item.getContent());
        setStyle(item.getState().getStyle());
        setDisable(!item.getState().isModifiable());
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);
        if(selected) setStyle(SELECTED_STYLE);
        else {
            if(!isEmpty())setStyle(getItem().getState().getStyle());
            else setStyle(DEFAULT_STYLE);
        }
    }


}
