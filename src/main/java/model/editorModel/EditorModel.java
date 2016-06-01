package model.editorModel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;

import javafx.beans.property.ReadOnlyObjectProperty;
import model.editorModel.contentNodeModel.ObservableCompResultModel;
import model.editorModel.contentNodeModel.ObservableCompResultInterface;
import model.editorModel.contentNodeModel.ObservableTextModel;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;
import model.fileIO.ComparisonTargetReader;
import model.fileIO.ComparisonTargetWriter;
import model.fileIO.file.ComparisonTargetInterface;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * 편집 창의 모델
 */
public class EditorModel implements EditorModelInterface {
	ComparisonTargetInterface comparisonTarget;
    ObservableTextModelInterface observableTextModelInterface;
    ObservableCompResultInterface observableCompResultInterface;
	
	public EditorModel(ComparisonTargetInterface comparisonTarget) {
		this.comparisonTarget = comparisonTarget;
        this.observableCompResultInterface = new ObservableCompResultModel(this.comparisonTarget);
        this.observableTextModelInterface = new ObservableTextModel(this.comparisonTarget);
	}

	@Override
	public boolean isFileLoaded() {
		if(comparisonTarget.getSource() == null) return false;
		else return true;
	}

	@Override
	public ReadOnlyObjectProperty<File> fileReadOnlyProperty() {
		return comparisonTarget.fileProperty();
	}

	@Override
	public void load(File source) throws IOException{
		try {
			ComparisonTargetReader loader = new ComparisonTargetReader();
			loader.readComparisonFile(source, comparisonTarget);
		}catch(MalformedInputException mie){
			comparisonTarget.setEncoding(null);
			comparisonTarget.setSource(null);
			comparisonTarget.setContent(null);
			throw mie;
		}
	}

	@Override
	public void save() throws IOException{
        ComparisonTargetWriter writer = new ComparisonTargetWriter();
        writer.writeComparisonFile(comparisonTarget);
	}

	@Override
	public ObservableTextModelInterface getObservableTextModel() {
		return observableTextModelInterface;
	}

	@Override
	public ObservableCompResultInterface getObservableListModel() {
		return observableCompResultInterface;
	}
}
