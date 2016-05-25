package model.fileIO.file;

import javafx.beans.property.*;
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
public class ObservableComparisonFile implements FilePropertyProvider, ObservableBlocksProvider, TextPropertyProvider{
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
                    .map(item->item.getContent())
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
        setSource(source);
        setContent(content);
    }

    // 이 네 개의 객체로 사용하면 됨.
    public void setSource(File sourceProperty) {
        this.sourceProperty.setValue(sourceProperty);
    }

    public void setContent(String content){
        this.contentProperty.setValue(content);
    }

    public File getSource() {
        return sourceProperty.getValue();
    }

    public String getContent() { return contentProperty.getValue(); }

    // 아래 세 메소드는 뷰가 사용해야 함.
    @Override
    public ObservableList<Block> getObservableBlocks() { return comparisonResult; }

    @Override
    public ReadOnlyObjectProperty<File> fileReadOnlyProperty() { return sourceProperty; }

    @Override
    public StringProperty textProperty(){
        return contentProperty;
    }
}
