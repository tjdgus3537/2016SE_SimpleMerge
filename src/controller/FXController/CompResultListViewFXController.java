package controller.FXController;

import controller.HighlightedListCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.diff.block.Block;
import model.editorModel.contentNodeModel.ObservableListModelInterface;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class CompResultListViewFXController implements Initializable, CompResultListViewControllerInterface{

    private ObservableListModelInterface model;
    @FXML
    private ListView<Block> compareListVIew;

    @Override
    public void setModel(ObservableListModelInterface model) {
        this.model = model;
        compareListVIew.setItems(model.getObservableBlocks());
    }

    @Override
    public void scrollTo(int value) {
        compareListVIew.scrollTo(value);
    }

    @Override
    public int getSelectedIndex(){
        return compareListVIew.getSelectionModel().getSelectedIndex();
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
