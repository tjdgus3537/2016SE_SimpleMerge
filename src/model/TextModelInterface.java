package model;

/**
 * Created by Seonghyeon on 5/22/2016.
 * read only mode와 edit mode에서 사용되는 data와 관련된 model에 대한 interface
 */

public interface TextModelInterface {
	/**
	 * left에 entity를 생성
	 * @param content 내용
	 * @param filePath 파일 경로
     */
	public void createLeft(String content, String filePath);
	/**
	 * right에 entity를 생성
	 * @param content 내용
	 * @param filePath 파일 경로
     */
	public void createRight(String content, String filePath);
	/**
	 * left의 content를 set한다
     */	
	public void setLeftContent(String content);
	/**
	 * right의 content를 set한다
     */		
	public void setRightContent(String content);
	/**
	 * left를 readOnly로 얻는다.
	 * @return readOnly 상태의 left entity
     */	
	public TextEntityReadOnlyInterface getLeftReadOnly();
	/**
	 * right를 readOnly로 얻는다.
	 * @return readOnly 상태의 right entity
     */		
	public TextEntityReadOnlyInterface getRightReadOnly();
}
