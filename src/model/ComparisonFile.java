package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.File;

/**
 * Created by Donghwan on 5/12/2016.
 * 비교하는 파일에 대해서 저장하는 클래스
 */
public class ComparisonFile{
    private File source;
    private StringProperty content;

    public ComparisonFile(File source, String content){
        this.content = new SimpleStringProperty();
        setSource(source);
        setContent(content);
    }

    public void setSource(File source) {
        this.source = source;
    }

    public void setContent(String content){
        this.content.setValue(content);
    }

    public File getSource() {
        return source;
    }

    public StringProperty textProperty(){
        return content;
    }

    public String getContentToString() {
        return content.getValue();
    }
}
