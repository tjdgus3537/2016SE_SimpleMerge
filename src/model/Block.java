package model;

/**
 * Created by Seonghyeon on 5/15/2016.
 * state가 동일한 주변의 line들을 묶은 단위.
 * 시작하는 줄의 번호 (startNumber), 줄의 개수(numberOfLine), 상태 여부 (state)를 갖는다.
 */

public class Block{
	//inner class로 enum 사용
	enum State{
		UNCHANGED, CHANGED, SPACE;
	}
	private int startNumber;
	private int numberOfLine;
	private State state;
	private String content;
	
	public Block() {
		setState(State.SPACE);
		setStartNumber(-1);
		setNumberOfLine(0);
		setContent(null);
	}
	
	public Block(int startNumber, int numberOfLine, State state, String content) {
		setState(state);
		setStartNumber(startNumber);
		setNumberOfLine(numberOfLine);
		setContent(content);
	}
	
	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}
	
	public void setNumberOfLine(int numberOfLine) {
		this.numberOfLine = numberOfLine;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getStartNumber() {
		return startNumber;
	}
	
	public int getNumberOfLine() {
		return numberOfLine;
	}
	
	public State getState() {
		return state;
	}
	
	public String getContent() {
		return content;
	}
}
