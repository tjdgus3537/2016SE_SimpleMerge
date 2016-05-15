package model;

/**
 * Created by Seonghyeon on 5/15/2016.
 * state가 동일한 주변의 line들을 묶은 단위.
 * 시작하는 줄의 번호 (startNumber), 줄의 개수(numberOfLine), 상태 여부 (state)를 갖는다.
 */

public class Block implements StateUsable{
	private int startNumber;
	private int numberOfLine;
	private int state;
	
	public Block() {
		setStartNumber(-1);
		setNumberOfLine(0);
		setState(SPACE);
	}
	
	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}
	
	public void setNumberOfLine(int numberOfLine) {
		this.numberOfLine = numberOfLine;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getStartNumber() {
		return startNumber;
	}
	
	public int getNumberOfLine() {
		return numberOfLine;
	}
	
	public int getState() {
		return state;
	}
}
