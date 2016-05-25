package model.editorModel;

import java.io.File;

import javafx.beans.property.ReadOnlyObjectProperty;
import model.editorModel.contentNodeModel.ObservableListModelInterface;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;
import model.fileIO.file.ObservableComparisonFile;

/**
 * Created by Donghwan on 5/25/2016.
 */
public class EditorModel implements EditorModelInterface {
	ObservableComparisonFile comparisonFile;
	
	public EditorModel(ObservableComparisonFile comparisonFile) {
		this.comparisonFile = comparisonFile;
	}

	@Override
	public ReadOnlyObjectProperty<File> fileReadOnlyProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load(File source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObservableTextModelInterface getObservableTextModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableListModelInterface getObservableListModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
