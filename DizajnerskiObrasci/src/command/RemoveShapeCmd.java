package command;


import mvc.DrawingModel;
import shapes.Shape;

public class RemoveShapeCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int shapeIndex;
	
	
	public RemoveShapeCmd(DrawingModel model, Shape shape, int i) {
		this.model = model;
		this.shape = shape;
		this.shapeIndex = i;
	}
	
	@Override
	public void execute() {
		this.model.remove(this.shape);
	}

	@Override
	public void unexecute() {
		this.model.getShapeList().add(this.shapeIndex, this.shape);
	}

	@Override
	public String toString() {
		return "Removed - " + this.shape + "\n";
	}
	
	

}
