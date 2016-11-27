package model.diff;

import java.util.ArrayList;

/**
 * Created by Sunghyeon on 11/25/2016.

 */

public class CommandInvoker {
	private ArrayList<Command> commands;
	
	public CommandInvoker() {
		commands = new ArrayList<Command>();
	}
	
	public void execute(Command command) {
		commands.add(command);
		command.execute();
	}
	
	public void undo() {
		if(commands.size() <= 0)
			return;
		
		Command command = commands.get(commands.size() - 1);
		command.undo();
		commands.remove(commands.size() - 1);
	}
	
	public void createLog() {
		//TODO:파일에 써줍시다.
	}
	
	public void clearList() {
		commands.clear();
	}
}
