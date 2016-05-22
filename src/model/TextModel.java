package model;

/**
 * Created by Seonghyeon on 5/22/2016.
 * read only mode와 edit mode에서 사용되는 data와 관련된 model
 */

public class TextModel implements TextModelInterface {
	//singleton으로 동작
	private static TextModel textModel = new TextModel();
	private TextEntity left;
	private TextEntity right;
	
	private TextModel() {
	}
	
	public static TextModel getInstance() {
		return textModel;
	}
	
	public void createLeft(String content, String filePath) {
		left = new TextEntity(content, filePath);
	}
	
	public void createRight(String content, String filePath) {
		right = new TextEntity(content, filePath);
	}
	
	public void setLeftContent(String content) {
		left.setContent(content);
	}
	
	public void setRightContent(String content) {
		right.setContent(content);
	}
	
	public TextEntityReadOnlyInterface getLeftReadOnly() {
		return left;
	}
	
	public TextEntityReadOnlyInterface getRightReadOnly() {
		return right;
	}
}
