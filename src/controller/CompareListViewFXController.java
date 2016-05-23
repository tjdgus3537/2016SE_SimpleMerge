package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.BlockInterface;
import model.ComparisonFile;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class CompareListViewFXController implements ContentNodeProvider {
    @FXML
    private ListView<BlockInterface> compareListVIew;

    public void setComparisonFile(ComparisonFile comparisonFile){
        compareListVIew.setItems(comparisonFile.getContent());
    }

    @Override
    public Node getContentNode() {
        return compareListVIew;
    }
}
