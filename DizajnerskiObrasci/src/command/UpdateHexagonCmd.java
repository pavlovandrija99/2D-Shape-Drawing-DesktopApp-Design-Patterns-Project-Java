package command;

import adapter.HexagonAdapter;
import shapes.Point;

public class UpdateHexagonCmd implements Command {

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter(new Point(0,0), 0);
	
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
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
		this.oldState.setHexagonCenter(this.original.getHexagonCenter());
		this.oldState.getHexagonCenter().setY(this.original.getHexagonCenter().getY());
		this.oldState.setHexagonRadius(this.original.getHexagonRadius());
		this.oldState.setHexagonBorderColor(this.original.getHexagonBorderColor());
		this.oldState.setHexagonInnerColor(this.original.getHexagonInnerColor());
	}


	@Override
	public String toString() {
		return "Modified - " + this.original + " " + "->" + " " + this.newState + "\n";
	}
	
	

}





