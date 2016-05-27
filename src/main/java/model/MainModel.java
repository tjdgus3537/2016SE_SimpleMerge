package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.diff.Copier;
import model.diff.CopierInterface;
import model.diff.Diff;
import model.diff.DiffInterface;
import model.diff.block.PairBlocks;
import model.editorModel.EditorModel;
import model.editorModel.EditorModelInterface;
import model.fileIO.file.ObservableComparisonFile;
import model.fileIO.file.ObservableComparisonFileInterface;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * 메인 창의 모델
 */
public class MainModel implements MainModelInterface {
	private ObservableComparisonFileInterface leftComparisonFile;
	private ObservableComparisonFileInterface rightComparisonFile;
	private EditorModelInterface leftEditorModel;
	private EditorModelInterface rightEditorModel;
	private DiffInterface diff;
	private CopierInterface copier;
	private BooleanProperty comparableProperty;
	
	public MainModel() {
		leftComparisonFile = new ObservableComparisonFile();
		rightComparisonFile = new ObservableComparisonFile();
		leftEditorModel = new EditorModel(leftComparisonFile);
		rightEditorModel = new EditorModel(rightComparisonFile);
		diff = new Diff();
		copier = new Copier();
		comparableProperty = new SimpleBooleanProperty(false);
		leftComparisonFile.fileProperty().addListener((observable, oldValue, newValue) -> {
			comparableProperty.setValue(isComparable());
		});
		rightComparisonFile.fileProperty().addListener((observable, oldValue, newValue) -> {
			comparableProperty.setValue(isComparable());
		});
	}

	private boolean isComparable(){
		if(leftComparisonFile.getSource() != null && rightComparisonFile.getSource() != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public BooleanProperty getComparableProperty() {
		return comparableProperty;
	}

	@Override
	public void compare() {
		PairBlocks pairBlocks = diff.compare(leftComparisonFile.getContent(), rightComparisonFile.getContent());
		leftComparisonFile.getObservableBlocks().setAll(pairBlocks.getLeft());
		rightComparisonFile.getObservableBlocks().setAll(pairBlocks.getRight());	
	}

	@Override
	public void copyToLeft(int blockNum) {
		copier.copyToLeft(leftComparisonFile.getObservableBlocks(), rightComparisonFile.getObservableBlocks(), blockNum);
	}

	@Override
	public void copyToRight(int blockNum) {
		copier.copyToRight(leftComparisonFile.getObservableBlocks(), rightComparisonFile.getObservableBlocks(), blockNum);
	}

	@Override
	public int size() {
		return leftComparisonFile.getObservableBlocks().size();
	}

	@Override
	public EditorModelInterface getLeftEditorModel() {
		return leftEditorModel;
	}

	@Override
	public EditorModelInterface getRightEditorModel() {
		return rightEditorModel;
	}
}
