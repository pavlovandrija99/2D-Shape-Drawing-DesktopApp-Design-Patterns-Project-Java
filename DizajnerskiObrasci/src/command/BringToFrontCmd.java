package command;

import java.util.ArrayList;
import java.util.Collections;

import mvc.DrawingModel;
import shapes.Shape;

public class BringToFrontCmd implements Command {

	private ArrayList<Shape> shapesList;
	private Shape shape;
	private int shapeIndex;
	
	
	public BringToFrontCmd(ArrayList<Shape> shapesList, Shape s) {
		this.shapesList = shapesList;
		this.shape = s;
		shapeIndex = this.shapesList.indexOf(this.shape);
	}
	
	@Override
	public void execute() {		
		for(int i = shapeIndex; i < this.shapesList.size() - 1; i++) {
			Collections.swap(this.shapesList, i, i+1);
		}
	}

	@Override
	public void unexecute() {
		for(int i = this.shapesList.size()-1; i > shapeIndex; i--) {
			Collections.swap(this.shapesList, i, i-1);
		}
	}

	@Override
	public String toString() {
		return "Bringed to front - " + this.shape + "\n";
	}
	
	

}






