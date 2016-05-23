package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Seonghyeon on 5/15/2016.
 * state가 동일한 주변의 line들을 묶은 단위.
 * 시작하는 줄의 번호 (startNumber), 줄의 개수(numberOfLine), 상태 여부 (state),
 * 내용(content)를 갖는다.
 */

public class Block implements BlockInterface {
	private State state;
	private StringProperty contentProperty;
	
	public Block() {
		setState(State.SPACE);
		this.contentProperty = new SimpleStringProperty();
	}
	
	public Block(State state, String content) {
		this();
		setState(state);
		setContent(content);
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void setContent(String content) {
		this.contentProperty.setValue(content);
	}

	@Override
	public int getNumberOfLines() { return StringUtils.countMatches(getContent(), '\n') + 1; }

	public StringProperty contentProperty(){
		return contentProperty;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public String getContent() {
		return contentProperty.getValue();
	}
}
