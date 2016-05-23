package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.BlockInterface;
import model.ComparisonFile;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class CompareListViewFXController implements Initializable, ContentNodeProvider {
    @FXML
    private ListView<BlockInterface> compareListVIew;

    public void setComparisonFile(ComparisonFile comparisonFile){
        compareListVIew.setItems(comparisonFile.getContent());
    }

    @Override
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
