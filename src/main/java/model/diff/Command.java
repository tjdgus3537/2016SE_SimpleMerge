package model.diff;

/**
 * Created by Sunghyeon on 11/25/2016.
 * Command 인터페이스
 */

public interface Command {
	public void execute();
	public void undo();
	public String getLog();
}
