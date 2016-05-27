package model.diff.block;

/**
 * Created by Seonghyeon on 5/15/2016.
 * state가 동일한 주변의 line들을 묶은 단위.
 * View에서 출력할 목적으로 사용하기 때문에 setter는 제공하지 않고 getter만 제공한다.
 * Block을 생성할 때만 생성자를 통해 값을 넣고, 그 이후에 Block을 수정할 수 없다.
 * 상태 여부 (state)와 내용(content)를 갖는다.
 */

public class Block{
	private State state;
	private String content;
	
	public Block() {
		setState(State.UNCHANGED);
		setContent(null);
	}

	public Block(State state, String content) {
		this();
		setState(state);
		setContent(content);
	}

	public State getState() {
		return state;
	}

	public String getContent() {
		return content;
	}
	
	private void setContent(String content) {
		this.content = content;
	}
	
	private void setState(State state) {
		this.state = state;
	}
}
