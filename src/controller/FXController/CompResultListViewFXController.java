package controller.FXController;

import controller.HighlightedListCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.diff.block.Block;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class CompResultListViewFXController implements Initializable, CompResultListViewControllerInterface{
    @FXML
    private ListView<Block> compareListVIew;

    @Override
    public void setContent(ObservableList<Block> content){
        compareListVIew.setItems(content);
    }

    @Override
    public void scrollTo(int value) {
        compareListVIew.scrollTo(value);
    }

    @Override
    public Node getContentNode() {
        return compareListVIew;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        compareListVIew.setCellFactory(listview-> new HighlightedListCell());
    }
}
