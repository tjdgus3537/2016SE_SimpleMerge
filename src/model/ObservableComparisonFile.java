package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.diff.block.Block;
import model.diff.block.State;

import java.io.File;
import java.util.stream.Collector;

/**
 * Created by Donghwan on 5/12/2016.
 * 비교하는 파일에 대해서 저장하는 클래스
 */
public class ObservableComparisonFile {
    private ObjectProperty<File> sourceProperty;
    private StringProperty contentProperty;
    private ObservableList<Block> comparisonResult;

    public ObservableComparisonFile() {
        // 처음 객체 초기화
        sourceProperty = new SimpleObjectProperty<>();
        contentProperty = new SimpleStringProperty();
        comparisonResult = FXCollections.observableArrayList();

        // 비교결과가 바뀔때마다 텍스트 모델에 변경 사항 저장
        comparisonResult.addListener((ListChangeListener<Block>) c -> {
            contentProperty.setValue(comparisonResult
                    .stream()
                    .filter(item -> item.getState() != State.SPACE)
                    .collect(
                            Collector.of(
                                    StringBuffer::new,
                                    StringBuffer::append,
                                    StringBuffer::append,
                                    StringBuffer::toString)
                    ));
        });
    }

    public ObservableComparisonFile(File source, String content){
        this();
        setSourceProperty(source);
        setContentProperty(content);
    }

    public void setSourceProperty(File sourceProperty) {
        this.sourceProperty.setValue(sourceProperty);
    }

    public void setContentProperty(String content){
        this.contentProperty.setValue(content);
    }

    public File getSourceProperty() {
        return sourceProperty.getValue();
    }

    public String getContentProperty() { return contentProperty.getValue(); }

    public ObservableList<Block> getComparisonResult() { return comparisonResult; }

    public ObjectProperty<File> sourcePropertyProperty() { return sourceProperty; }

    public StringProperty textProperty(){
        return contentProperty;
    }
}
