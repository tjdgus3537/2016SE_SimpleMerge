package model;

/**
 * Created by Seonghyeon on 5/22/2016.
 * 상태에 대한 enum class
 */

public enum State {
	UNCHANGED(""), CHANGED("-fx-background-color: #EFCB05"), SPACE("-fx-background-color: #C0C0C0");
	private final String style;

	private State(String style){
		this.style = style;
	}

	public String getStyle(){ return style; }
}
