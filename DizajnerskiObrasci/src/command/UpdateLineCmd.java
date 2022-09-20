package command;

import shapes.Line;

public class UpdateLineCmd implements Command {

	
	private Line oldState;
	private Line newState;
	private Line original = new Line();
	
	
	public UpdateLineCmd(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	
	@Override
	public void execute() {
		this.original = this.oldState.clone(original);
		this.oldState = this.newState.clone(oldState);
	}

	
	@Override
	public void unexecute() {
		this.oldState = this.original.clone(oldState);
	}


	@Override
	public String toString() {
		return "Modified - " + this.original + " " + "->" + " " + this.newState + "\n";
	}

	
	
	
}
