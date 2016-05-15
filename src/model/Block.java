package model;

/**
 * Created by Seonghyeon on 5/15/2016.
 * state가 동일한 주변의 line들을 묶은 단위.
 * 시작하는 줄의 번호 (startNumber), 줄의 개수(numberOfLine), 상태 여부 (state)를 갖는다.
 * SPACE 상태를 갖는 block은 시작 줄은 의미가 없고, 줄의 개수만 의미를 갖는다.
 * 즉, SPACE 상태일 때는 항상 startNumber가 -1 이다.
 */

public class Block implements StateUsable{
	private int startNumber;
	private int numberOfLine;
	private int state;
	
	public Block() {
		setState(SPACE);
		setStartNumber(-1);
		setNumberOfLine(0);
	}
	
	public Block(int startNumber, int numberOfLine, int state) {
		setState(state);
		setStartNumber(startNumber);
		setNumberOfLine(numberOfLine);
	}
	
	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
		if(state == SPACE)
			this.startNumber = -1;
	}
	
	public void setNumberOfLine(int numberOfLine) {
		this.numberOfLine = numberOfLine;
	}
	
	public void setState(int state) {
		this.state = state;
		if(state == SPACE)
			setStartNumber(-1);
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
