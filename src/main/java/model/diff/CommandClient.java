package model.diff;

import java.util.ArrayList;
import java.util.List;

import model.diff.block.Block;

/**
 * Created by Sunghyeon on 11/25/2016.

 */

public class CommandClient {
	private CommandInvoker commandInvoker;
	
	public CommandClient() {
		commandInvoker = new CommandInvoker();
	}
	
	public void copyToLeft(List<Block> left, List<Block> right, int blockNum) {
		commandInvoker.execute(new CopyToLeftCommand(left, right, blockNum));
	}
	
	public void copyToRight(List<Block> left, List<Block> right, int blockNum) {
		commandInvoker.execute(new CopyToRightCommand(left, right, blockNum));
	}
		
	public void undo() {
		commandInvoker.undo();
	}
	
	public String createLog() {
		commandInvoker.createLog();
		return null;
	}
	
	public void reset() {
		commandInvoker.clearList();
	}
}
