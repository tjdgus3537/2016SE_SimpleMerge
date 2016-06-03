package model.fileIO.file;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.diff.block.Block;
import model.diff.block.CompState;

import java.io.File;
import java.nio.charset.Charset;
import java.util.stream.Collector;

/**
 * Created by Donghwan on 5/12/2016.
 * 비교하는 파일에 대해서 저장하는 클래스
 */
public class ComparisonTarget implements ComparisonTargetInterface {
    private final ObjectProperty<File> sourceProperty;
    private final StringProperty contentProperty;
    private final ObservableList<Block> comparisonResult;
    private Charset encoding;

    public ComparisonTarget() {
        // 처음 객체 초기화
        sourceProperty = new SimpleObjectProperty<>(null);
        contentProperty = new SimpleStringProperty(null);
        comparisonResult = FXCollections.observableArrayList();

        // 비교결과가 바뀔때마다 텍스트 모델에 변경 사항 저장
        comparisonResult.addListener(
                (ListChangeListener<Block>) c -> contentProperty.setValue(comparisonResult
                .stream()
                .filter(item -> item.getState() != CompState.SPACE)
                .map(Block::getContent)
                .collect(
                        Collector.of(
                                StringBuffer::new,
                                StringBuffer::append,
                                StringBuffer::append,
                                StringBuffer::toString)
                )));
    }

    public ComparisonTarget(File source, String content){
        this();
        setSource(source);
        setContent(content);
    }

    // 이 네 개의 객체로 사용하면 됨.
    @Override
    public void setSource(File source) {
        this.sourceProperty.setValue(source);
    }

    @Override
    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }

    @Override
    public void setContent(String content){
        this.contentProperty.setValue(content);
    }

    @Override
    public File getSource() {
        return sourceProperty.getValue();
    }

    @Override
    public String getContent() { return contentProperty.getValue(); }

    @Override
    public Charset getEncoding() {
        return encoding;
    }

    // 아래 세 메소드는 뷰가 사용해야 함.
    @Override
    public ObservableList<Block> getObservableBlocks() { return comparisonResult; }

    @Override
    public ObjectProperty<File> fileProperty() { return sourceProperty; }

    @Override
    public StringProperty textProperty(){
        return contentProperty;
    }
}
