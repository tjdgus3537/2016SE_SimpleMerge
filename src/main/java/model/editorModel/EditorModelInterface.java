package model.editorModel;

import model.editorModel.contentNodeModel.ObservableListModelInterface;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;
import model.fileIO.file.FilePropertyProvider;

import java.io.File;
import java.io.IOException;

/**
 * Created by Donghwan on 5/25/2016.
 */
public interface EditorModelInterface extends FilePropertyProvider {
    /**
     * 해당 파일을 불러온다
     * @param source 불러올 파일
     * @throws IOException 불러오기에 실패하면 발생한다.
     */
    void load(File source) throws IOException;

    /**
     * 편집 중인 파일을 저장한다.
     * @throws IOException 저장하는 데 실패하면 발생한다.
     */
    void save() throws IOException;

    /**
     * 텍스트 표시와 관련된 모델을 반환한다.
     * @return 텍스트 표시를 해주는 모델
     */
    ObservableTextModelInterface getObservableTextModel();

    /**
     * 리스트 표시와 관련된 모델을 반환한다.
     * @return 리스트 표시를 해주는 모델
     */
    ObservableListModelInterface getObservableListModel();
}
