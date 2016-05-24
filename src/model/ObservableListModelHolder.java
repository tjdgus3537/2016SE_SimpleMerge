package model;

import javafx.collections.ObservableList;
import model.BlockReadInterface;

/**
 * Created by Donghwan on 5/24/2016.
 */
public interface ObservableListModelHolder {
    void setContent(ObservableList<BlockReadInterface> content);
    ObservableList<BlockReadInterface> getContent();
    String getContentToString();
}
