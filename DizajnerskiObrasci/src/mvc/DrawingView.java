package mvc;


import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import shapes.Shape;



public class DrawingView extends JPanel{
	public DrawingView() {
	}
	
	private DrawingModel model = new DrawingModel();    /* Po MVC-u kreiranje objekta DrawingModel 
								       					nije potrebno, kreiramo objekat zbog
														DrawingFrame-a, tj. zbog WindowBuildera koji pravi problem  */
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapeList().iterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
	}

	
}
	
