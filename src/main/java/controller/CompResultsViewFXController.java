package controller;

import controller.Interface.CompResultsViewControllerInterface;
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
 * 비교 결과를 보여주는 뷰의 컨트롤러
 *
 */
public class CompResultsViewFXController implements Initializable, CompResultsViewControllerInterface {

    private ObservableListModelInterface model;
    @FXML
    private ListView<Block> compareListVIew;

    @Override
    public void setModel(ObservableListModelInterface model) {
        this.model = model;
        compareListVIew.setItems(model.getObservableBlocks());
    }

    @Override
    public void scrollTo(int index) {
        compareListVIew.scrollTo(index);
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
        // 비교 결과를 하이라이팅해서 표시
        compareListVIew.setCellFactory(listView-> new CompStateHighlightedListCell());
    }
}
