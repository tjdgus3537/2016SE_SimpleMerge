package model;

/**
 * Created by Seonghyeon on 5/21/2016.
 * Block을 직접적으로 쓰지 않고 읽기용으로만 사용할 때 필요한 Interface.
 */

public interface BlockReadInterface {
	/**
	 * block의 상태가 changed 상태인지를 확인한다.
     * @return changed이면 true 아니면 false
     */
	public boolean isChanged();
	/**
	 * block의 상태가 unchanged 상태인지를 확인한다.
     * @return unchanged이면 true 아니면 false
     */
	public boolean isUnchanged();
	/**
	 * block의 상태가 space 상태인지를 확인한다.
     * @return space이면 true 아니면 false
     */
	public boolean isSpace();
	/**
	 * block의 Content 내용을 얻는다.
     * @return String 형태의 Content를 return
     */
	public String getContent();
}
