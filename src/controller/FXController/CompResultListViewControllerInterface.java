package controller.FXController;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import model.Block;

/**
 * Created by Donghwan on 5/25/2016.
 */
public interface CompResultListViewControllerInterface extends ContentNodeProvider {
    void setContent(ObservableList<Block> content);
    void scrollTo(int value);
    @Override
    Node getContentNode();
}
