package model;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.stream.Collector;

/**
 * Created by Donghwan on 5/12/2016.
 * 비교하는 파일에 대해서 저장하는 클래스
 */
public class ComparisonFile implements TextPropertyProvider, ObservableBlocksProvider{
    private File source;
    private ObservableList<BlockReadInterface> content;

    public ComparisonFile(File source, String content){
        setSource(source);
        setContent(content);
    }

    public ComparisonFile(File source, ObservableList<BlockReadInterface> content) {
        setSource(source);
        setContent(content);
    }

    public void setSource(File source) {
        this.source = source;
    }

    public void setContent(ObservableList<BlockReadInterface> content) {
        this.content = content;
    }

    public void setContent(String content){
        if(this.content != null) this.content.clear();
        else this.content = FXCollections.observableArrayList();
        Block block = new Block(content);
        this.content.add(block);
    }

    public File getSource() {
        return source;
    }

    @Override
    public ObservableList<BlockReadInterface> getContent() {
        return content;
    }

    @Override
    public StringProperty textProperty(){
        Block singleBlock = new Block(content.stream().filter(block->block.getState() != State.SPACE).collect(Collector.of(()->new StringBuffer(), (buf, item)-> buf.append(item.getContent()), (buf1, buf2) -> buf1.append(buf2), StringBuffer::toString)));
        content.clear();
        content.add(singleBlock);
        return singleBlock.contentProperty();
    }

    public String getContentToString() {
        return content.stream().filter(block->block.getState() != State.SPACE).collect(Collector.of(()->new StringBuffer(), (buf, item)-> buf.append(item.getContent()), (buf1, buf2) -> buf1.append(buf2), StringBuffer::toString));
    }
}
