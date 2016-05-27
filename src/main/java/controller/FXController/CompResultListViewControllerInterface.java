package controller.FXController;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import model.diff.block.Block;
import model.editorModel.contentNodeModel.ObservableListModelInterface;

/**
 * Created by Donghwan on 5/25/2016.
 */
public interface CompResultListViewControllerInterface extends ContentNodeProvider {
    void setModel(ObservableListModelInterface model);
    void scrollTo(int value);
    int getSelectedIndex();
}
