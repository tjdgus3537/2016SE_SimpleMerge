package model;

/**
 * Created by Seonghyeon on 5/21/2016.
 * Block을 직접적으로 쓰지 않고 읽기용으로만 사용할 때 필요한 Interface.
 */

public interface BlockReadInterface {
	State getState();
	/**
	 * block의 Content 내용을 얻는다.
     * @return String 형태의 Content를 return
     */
	String getContent();
}
