package model;

import javafx.collections.ObservableList;

/**
 * Created by Donghwan on 5/24/2016.
 */
public interface ObservableBlocksProvider {
    ObservableList<BlockReadInterface> getContent();
}
