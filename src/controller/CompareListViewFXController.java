package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.BlockReadInterface;

import java.util.List;

/**
 * Created by Donghwan on 5/22/2016.
 */
public class CompareListViewFXController implements ContentHolder {
    private ObservableList<BlockReadInterface> model;
    @FXML
    private ListView<BlockReadInterface> compareListVIew;

    public void setModel(ObservableList<BlockReadInterface> model){
        this.model = model;
        compareListVIew.setItems(model);
    }

    public ObservableList<BlockReadInterface> getModel(){
        return model;
    }

    @Override
    public String getContentString() {
        return model.toString();
    }
}
