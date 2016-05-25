package model;

import model.editorModel.EditorModelInterface;

/**
 * Created by Donghwan on 5/25/2016.
 * MainModel을 가져다 쓰는 객체들을 위한 인터페이스
 */
public interface MainModelInterface {
    /**
     * 비교가 가능한 지 확인함
     * @return 비교가 가능하면 true, 아니면 false를 반환한다.
     */
    boolean isReadyToCompare();

    /**
     * 모델에 저장된 두 파일 내용을 비교해서 비교 결과에 그 내용을 저장한다.
     * 만약 이미 결과가 있는 경우, 리스트의 모든 내용을 지우고 새로운 결과로 덮어 씌운다.
     */
    void compare();

    /**
     * 오른쪽 파일의 내용 중 해당되는 블럭을 왼쪽 파일에 복사한다.
     * @param blockNum 오른쪽 파일에서 선택한 블럭의 번호
     */
    void copyToLeft(int blockNum);

    /**
     * 왼쪽 파일의 내용 중 해당되는 블럭을 오른쪽 파일에 복사한다.
     * @param blockNum 왼쪽 파일에서 선택한 블럭의 번호
     */
    void copyToRight(int blockNum);

    /**
     * 비교 결과의 블럭 개수를 반환한다.
     * @return 비교 결과의 블럭 개수
     */
    int size();

    /**
     * 왼쪽 편집창의 로직을 처리하는 모델을 반환한다
     * @return 왼쪽 편집창의 모델
     */
    EditorModelInterface getLeftEditorModel();

    /**
     * 오른쪽 편집창의 로직을 처리하는 모델을 반환한다.
     * @return 오른쪽 편집창의 모델
     */
    EditorModelInterface getRightEditorModel();
}
