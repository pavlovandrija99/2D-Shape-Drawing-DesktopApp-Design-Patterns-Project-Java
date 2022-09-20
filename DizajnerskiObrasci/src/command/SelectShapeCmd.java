package command;

import mvc.DrawingController;
import shapes.Shape;

public class SelectShapeCmd implements Command {

	private DrawingController controller;
	private Shape shape;
	
	public SelectShapeCmd(DrawingController c, Shape s) {
		this.controller = c;
		this.shape = s;
	}
	
	
	@Override
	public void execute() {
		this.shape.setSelected(true);
		this.controller.getSelectedShapesList().add(this.shape);
	}

	@Override
	public void unexecute() {
		this.shape.setSelected(false);
		this.controller.getSelectedShapesList().remove(this.shape);
	}


	@Override
	public String toString() {
		return "Selected - " + this.shape + "\n";
	}
	
	

}
