package model.fileIO.file;

import javafx.collections.ObservableList;
import model.diff.block.Block;

/**
 * Created by Donghwan on 5/26/2016.
 *
 * ObservableList 형태의 Block 리스트를 제공하는 클래스의 인터페이스
 */
public interface ObservableBlocksProvider {
    /**
     * Block의 ObservableList를 반환함
     * @return Block의 ObservableList
     */
    ObservableList<Block> getObservableBlocks();
}
