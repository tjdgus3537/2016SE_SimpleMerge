package controller.FXController;

import controller.CompareModeDisabler;
import model.editorModel.EditorModelInterface;

/**
 * Created by Donghwan on 5/26/2016.
 */
public interface EditorPaneControllerInterface extends ContentNodeProvider{
    void setModel(EditorModelInterface model);
    void scrollTo(int value);
    int getSelectedIndex();
    void switchCompareListView();
    void setCompareModeDisabler(CompareModeDisabler compareModeDisabler);
}
