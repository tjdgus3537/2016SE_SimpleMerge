package model.editorModel;

import model.editorModel.contentNodeModel.ObservableListModelInterface;
import model.editorModel.contentNodeModel.ObservableTextModelInterface;
import model.fileIO.file.FilePropertyProvider;

import java.io.File;

/**
 * Created by Donghwan on 5/25/2016.
 */
public interface EditorModelInterface extends FilePropertyProvider {
    /**
     * 해당 파일을 불러온다
     * @param source 불러올 파일
     */
    void load(File source);

    /**
     * 편집 중인 파일을 저장한다.
     */
    void save();

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
