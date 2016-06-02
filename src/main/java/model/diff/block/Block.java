package model.diff.block;

/**
 * Created by Seonghyeon on 5/15/2016.
 *
 * state가 동일한 주변의 line들을 묶은 단위.
 * View에서 출력할 목적으로 사용하기 때문에 setter는 제공하지 않고 getter만 제공한다.
 * Block을 생성할 때만 생성자를 통해 값을 넣고, 그 이후에 Block을 수정할 수 없다.
 * 상태 여부 (state)와 내용(content)를 갖는다.
 */

public class Block{
	private final CompState state;
	private final String content;

	public Block(CompState state, String content) {
		this.content = content;
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Block block = (Block) o;

		if (getState() != block.getState()) return false;
		return getContent() != null ? getContent().equals(block.getContent()) : block.getContent() == null;

	}

	@Override
	public int hashCode() {
		int result = getState() != null ? getState().hashCode() : 0;
		result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
		return result;
	}

	public CompState getState() {
		return state;
	}

	public String getContent() {
		return content;
	}
}
