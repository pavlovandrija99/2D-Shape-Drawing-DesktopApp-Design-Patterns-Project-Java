package command;

import java.util.ArrayList;
import java.util.Collections;

import mvc.DrawingModel;
import shapes.Shape;

public class BringToBackCmd implements Command {

	private ArrayList<Shape> shapesList;
	private Shape shape;
	private int shapeIndex;
	
	public BringToBackCmd(ArrayList<Shape> shapesList, Shape shape) {
		this.shapesList = shapesList;
		this.shape = shape;
		shapeIndex = this.shapesList.indexOf(this.shape);
	}
	
	
	@Override
	public void execute() {
		for(int i = shapeIndex; i > 0; i--) {
			Collections.swap(this.shapesList, i, i-1);
		}
		
	}

	@Override
	public void unexecute() {
		for(int i = 0; i < shapeIndex; i++) {
			Collections.swap(this.shapesList, i, i+1);
		}
	}


	@Override
	public String toString() {
		return "Bringed to back - " + this.shape + "\n"; 
	}
	
	

}
