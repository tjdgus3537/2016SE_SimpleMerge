package model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.stream.Collector;

/**
 * Created by Donghwan on 5/12/2016.
 * 비교하는 파일에 대해서 저장하는 클래스
 */
public class ComparisonFile {
    private File source;
    private ObservableList<BlockInterface> content;

    public ComparisonFile(File source, String content){
        setSource(source);
        setContent(content);
    }

    public ComparisonFile(File source, ObservableList<BlockInterface> content) {
        setSource(source);
        setContent(content);
    }

    public void setSource(File source) {
        this.source = source;
    }

    public void setContent(ObservableList<BlockInterface> content) {
        this.content = content;
    }

    public void setContent(String content){
        this.content.clear();
        this.content.add(new Block(State.UNCHANGED, content));
    }

    public File getSource() {
        return source;
    }

    public ObservableList<BlockInterface> getContent() {
        return content;
    }

    public StringProperty getTextAreaProperty(){
        Block singleBlock = new Block(State.UNCHANGED, content.stream().collect(Collector.of(()->new StringBuffer(), (buf, item)-> buf.append(item), (buf1, buf2) -> buf1.append(buf2), StringBuffer::toString)));
        content.clear();
        content.add(singleBlock);
        return singleBlock.contentProperty();
    }

    public String getContentToString() {
        return content.stream().collect(Collector.of(()->new StringBuffer(), (buf, item)-> buf.append(item), (buf1, buf2) -> buf1.append(buf2), StringBuffer::toString));
    }
}
