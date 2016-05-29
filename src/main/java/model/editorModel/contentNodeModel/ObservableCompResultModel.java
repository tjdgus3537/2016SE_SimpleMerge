package model.editorModel.contentNodeModel;

import javafx.collections.ObservableList;
import model.diff.block.Block;
import model.fileIO.file.ObservableBlocksProvider;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * Observable 인터페이스를 상속받은 비교 결과를 제공하는 모델
 */
public class ObservableCompResultModel implements ObservableCompResultInterface {
    private ObservableBlocksProvider compResult;

    public ObservableCompResultModel(ObservableBlocksProvider compResult) {
        this.compResult = compResult;
    }

    @Override
    public ObservableList<Block> getObservableBlocks() {
        return compResult.getObservableBlocks();
    }
}
