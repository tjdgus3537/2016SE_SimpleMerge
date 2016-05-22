package model;

/**
 * Created by Seonghyeon on 5/22/2016.
 * read only mode와 edit mode에서 사용되는 data와 관련된 model에 대한 interface
 */

//@TODO:: 지금 interface를 한 군데 모아서 써놓긴 했는데 쓰는 곳마다 다르니까 interface를 나눠서 써야할듯
//예를 들어 view는 create나 set을 쓸 일이 없고, DiffCommand에서는 create를 쓸 일이 없고
//controller에서는 get을 쓸 일이 없음.
public interface TextModelInterface {
	//@TODO:: create는 load할 때 발생할 듯, 추후 이 주석은 삭제
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
