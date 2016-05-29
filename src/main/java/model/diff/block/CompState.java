package model.diff.block;

/**
 * Created by Seonghyeon on 5/22/2016.
 *
 * 상태에 대한 enum class
 */

public enum CompState {
	UNCHANGED("", false), CHANGED("-fx-background-color: #EFCB05", true), SPACE("-fx-background-color: #C0C0C0", true);
	private final String style;
	private final boolean modifiable;

	CompState(String style, boolean modifiable){
		this.style = style;
		this.modifiable = modifiable;
	}

	public String getStyle() {
		return style; 
	}
	public boolean isModifiable() {
		return modifiable;
	}
}
