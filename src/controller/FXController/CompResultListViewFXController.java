package controller.FXController;

import controller.HighlightedListCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.Block;
import model.State;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class CompResultListViewFXController implements Initializable{
    @FXML
    private ListView<Block> compareListVIew;

    public void setContent(ObservableList<Block> content){
        compareListVIew.setItems(content);
    }

    public Node getContentNode() {
        return compareListVIew;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        compareListVIew.setCellFactory(listview-> new HighlightedListCell());
    }
}
