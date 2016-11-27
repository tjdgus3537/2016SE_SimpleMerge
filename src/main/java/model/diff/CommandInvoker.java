package model.diff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("log.txt", true));
			
			//이터레이터를 사용함(copyToLeftCommand인지 copyToRightCommand인지 구분할 필요 X)
			for(Command command : commands) {
				out.write(command.getLog());
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearList() {
		commands.clear();
	}
}
