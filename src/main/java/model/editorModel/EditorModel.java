package model.editorModel;

import java.io.File;
import java.io.IOException;
import javafx.beans.property.ReadOnlyObjectProperty;
import model.editorModel.contentNodeModel.ObservableCompResultModel;
import model.editorModel.contentNodeModel.ObservableCompResultInterface;
import model.editorModel.contentNodeModel.ObservableTextModel;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;
import model.fileIO.AbstractComparisonTargetLoader;
import model.fileIO.ComparisonTargetLoader;
import model.fileIO.ComparisonTargetWriter;
import model.fileIO.file.ComparisonTargetInterface;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * 편집 창의 모델
 */
public class EditorModel implements EditorModelInterface {
	private final ComparisonTargetInterface comparisonTarget;
    private final ObservableTextModelInterface observableTextModelInterface;
    private final ObservableCompResultInterface observableCompResultInterface;
	
	public EditorModel(ComparisonTargetInterface comparisonTarget) {
		this.comparisonTarget = comparisonTarget;
        this.observableCompResultInterface = new ObservableCompResultModel(this.comparisonTarget);
        this.observableTextModelInterface = new ObservableTextModel(this.comparisonTarget);
	}

	@Override
	public boolean isFileLoaded() {
		return comparisonTarget.getSource() != null;
	}

	@Override
	public ReadOnlyObjectProperty<File> fileReadOnlyProperty() {
		return comparisonTarget.fileProperty();
	}

	@Override
	public void load(File source) throws IOException{
		AbstractComparisonTargetLoader loader = new ComparisonTargetLoader();
		loader.load(source, comparisonTarget);
	}

	@Override
	public void save() throws IOException{
        ComparisonTargetWriter writer = new ComparisonTargetWriter();
        writer.write(comparisonTarget);
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
