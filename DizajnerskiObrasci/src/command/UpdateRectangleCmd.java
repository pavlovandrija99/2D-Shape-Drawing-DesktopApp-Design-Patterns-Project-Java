package command;

import shapes.Rectangle;

public class UpdateRectangleCmd implements Command {

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle();
	
	
	public UpdateRectangleCmd(Rectangle oldState, Rectangle newState) {
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
