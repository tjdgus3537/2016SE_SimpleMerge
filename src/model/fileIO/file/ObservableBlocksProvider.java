package model.fileIO.file;

import javafx.collections.ObservableList;
import model.diff.block.Block;

/**
 * Created by Donghwan on 5/26/2016.
 */
public interface ObservableBlocksProvider {
    /**
     * Block의 ObservableList를 반환함
     * @return Block의 ObservableList
     */
    ObservableList<Block> getObservableBlocks();
}
