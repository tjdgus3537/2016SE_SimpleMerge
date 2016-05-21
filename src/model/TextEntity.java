package model;

/**
 * Created by Seonghyeon on 5/22/2016.
 * read only mode와 edit mode에서 사용되는 data
 */

public class TextEntity implements TextEntityReadOnlyInterface{
	private String content;
	private String filePath;
	
	//처음에 filePath가 없으면 존재 불가능
	public TextEntity(String content, String filePath) {
		setContent(content);
		setFilePath(filePath);
	}
	
	public String getContent() {
		return content;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
