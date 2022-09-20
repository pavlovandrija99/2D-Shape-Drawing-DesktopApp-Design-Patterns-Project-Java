package command;

import shapes.Donut;

public class UpdateDonutCmd implements Command {

	
	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();
	
	
	public UpdateDonutCmd(Donut oldState, Donut newState) {
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







