package controller.FXController;

import controller.HighlightedListCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.BlockReadInterface;
import model.ComparisonFile;
import model.ObservableBlocksProvider;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class CompareListViewFXController implements Initializable{
    @FXML
    private ListView<BlockReadInterface> compareListVIew;

    public void setContent(ObservableBlocksProvider comparisonFile){
        compareListVIew.setItems(comparisonFile.getContent());
    }

    public Node getContentNode() {
        return compareListVIew;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        compareListVIew.setCellFactory((listview)->{
            return new HighlightedListCell();
        });
    }
}
