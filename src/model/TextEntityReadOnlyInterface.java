package model;

/**
 * Created by Seonghyeon on 5/22/2016.
 * read only mode와 edit mode에서 사용되는 data를 읽기만 하는 경우에 사용하는 interface
 */

public interface TextEntityReadOnlyInterface {
	/**
	 * content 값을 얻어온다
     * @return content
     */
	public String getContent();
	/**
	 * filePath 값을 얻어온다
     * @return filePath
     */
	public String getFilePath();
}
