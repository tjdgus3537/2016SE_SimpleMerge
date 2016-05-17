package model;

import java.io.File;

/**
 * Created by Donghwan on 5/12/2016.
 * @deprecated EditorPaneFXController에서 더 이상 ComparisonFile을 사용하지 않습니다.
 * 비교하는 파일에 대해서 저장하는 클래스
 */
public class ComparisonFile {
    private File source;
    private StringBuffer content;

    public ComparisonFile(File source, StringBuffer content) {
        this.source = source;
        this.content = content;
    }

    public File getSource() {
        return source;
    }

    public StringBuffer getContent() {
        return content;
    }
}
