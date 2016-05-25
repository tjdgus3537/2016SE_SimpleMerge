package model.editorModel.contentNodeModel;

import javafx.collections.ObservableList;
import model.diff.block.Block;
import model.fileIO.file.ObservableBlocksProvider;
import model.fileIO.file.ObservableComparisonFile;
import model.fileIO.file.TextPropertyProvider;

/**
 * Created by Donghwan on 5/25/2016.
 */
public class ObservableListModel implements ObservableListModelInterface {
    private ObservableBlocksProvider comparisonFile;

    public ObservableListModel(ObservableBlocksProvider comparisonFile) {
        this.comparisonFile = comparisonFile;
    }

    @Override
    public ObservableList<Block> getObservableBlocks() {
        return comparisonFile.getObservableBlocks();
    }
}
