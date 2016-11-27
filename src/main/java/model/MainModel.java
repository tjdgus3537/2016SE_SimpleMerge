package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.diff.CommandClient;
import model.diff.Diff;
import model.diff.DiffInterface;
import model.diff.block.PairBlocks;
import model.editorModel.EditorModel;
import model.editorModel.EditorModelInterface;
import model.fileIO.file.ComparisonTarget;
import model.fileIO.file.ComparisonTargetInterface;

/**
 * Created by Donghwan on 5/25/2016.
 *
 * 메인 창의 모델
 */
public class MainModel implements MainModelInterface {
	private ComparisonTargetInterface leftComparisonTarget;
	private ComparisonTargetInterface rightComparisonTarget;
	private EditorModelInterface leftEditorModel;
	private EditorModelInterface rightEditorModel;
	private DiffInterface diff;
	private BooleanProperty comparableProperty;
	private CommandClient commandClient;
	
	public MainModel() {
		leftComparisonTarget = new ComparisonTarget();
		rightComparisonTarget = new ComparisonTarget();
		leftEditorModel = new EditorModel(leftComparisonTarget);
		rightEditorModel = new EditorModel(rightComparisonTarget);
		diff = new Diff();
		comparableProperty = new SimpleBooleanProperty(false);
		leftComparisonTarget.fileProperty().addListener((observable, oldValue, newValue) -> {
			comparableProperty.setValue(isComparable());
		});
		rightComparisonTarget.fileProperty().addListener((observable, oldValue, newValue) -> {
			comparableProperty.setValue(isComparable());
		});
		commandClient = new CommandClient();
	}

	private boolean isComparable(){
		return leftComparisonTarget.getSource() != null && rightComparisonTarget.getSource() != null;
	}

	@Override
	public BooleanProperty getComparableProperty() {
		return comparableProperty;
	}

	@Override
	public void compare() {
		PairBlocks pairBlocks = diff.compare(leftComparisonTarget.getContent(), rightComparisonTarget.getContent());
		leftComparisonTarget.getObservableBlocks().setAll(pairBlocks.getLeft());
		rightComparisonTarget.getObservableBlocks().setAll(pairBlocks.getRight());
	}

	@Override
	public void copyToLeft(int blockNum) {
		commandClient.copyToLeft(leftComparisonTarget.getObservableBlocks(), rightComparisonTarget.getObservableBlocks(), blockNum);
	}

	@Override
	public void copyToRight(int blockNum) {
		commandClient.copyToRight(leftComparisonTarget.getObservableBlocks(), rightComparisonTarget.getObservableBlocks(), blockNum);
	}

	@Override
	public void undo() {
		commandClient.undo();
	}

	@Override
	public void createLog() {
		commandClient.createLog();
	}

	@Override
	public void disableCompMode() {
		commandClient.createLog();
		commandClient.reset();
	}

	@Override
	public int size() {
		return Integer.min(leftComparisonTarget.getObservableBlocks().size(), rightComparisonTarget.getObservableBlocks().size());
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
