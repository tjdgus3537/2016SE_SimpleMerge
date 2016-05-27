package model.editorModel;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.ReadOnlyObjectProperty;
import model.editorModel.contentNodeModel.ObservableCompResultModel;
import model.editorModel.contentNodeModel.ObservableCompResultInterface;
import model.editorModel.contentNodeModel.ObservableTextModel;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;
import model.fileIO.ComparisonFileReader;
import model.fileIO.ComparisonFileWriter;
import model.fileIO.file.ObservableComparisonFile;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * 편집 창의 모델
 */
public class EditorModel implements EditorModelInterface {
	ObservableComparisonFile comparisonFile;
    ObservableTextModelInterface observableTextModelInterface;
    ObservableCompResultInterface observableCompResultInterface;
	
	public EditorModel(ObservableComparisonFile comparisonFile) {
		this.comparisonFile = comparisonFile;
        this.observableCompResultInterface = new ObservableCompResultModel(this.comparisonFile);
        this.observableTextModelInterface = new ObservableTextModel(this.comparisonFile);
	}

	@Override
	public ReadOnlyObjectProperty<File> fileReadOnlyProperty() {
		return comparisonFile.fileReadOnlyProperty();
	}

	@Override
	public void load(File source) throws IOException{
        ComparisonFileReader reader = new ComparisonFileReader();
        ObservableComparisonFile comparisonFile = reader.readComparisonFile(source);
        this.comparisonFile.setSource(comparisonFile.getSource());
        this.comparisonFile.setContent(comparisonFile.getContent());
	}

	@Override
	public void save() throws IOException{
        ComparisonFileWriter writer = new ComparisonFileWriter();
        writer.writeComparisonFile(comparisonFile);
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
