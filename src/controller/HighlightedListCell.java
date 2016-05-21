package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import model.Block;

/**
 * Created by Donghwan on 5/20/2016.
 * 리스트 뷰에서 비교 결과를 하이라이팅하는 셀
 */
public class HighlightedListCell extends ListCell<Block> {
    @Override
    protected void updateItem(Block item, boolean empty) {
        super.updateItem(item, empty);
        setText(item.getContent());
        ObservableList<String> styleList = getStyleClass();
        styleList.removeAll("changed", "unchanged", "space");
        if(item.isChanged()) {
            styleList.add("changed");
        }else if(item.isSpace()){
            styleList.add("space");
        }else if(item.isUnchanged()){
            styleList.add("unchanged");
        }
    }
}
