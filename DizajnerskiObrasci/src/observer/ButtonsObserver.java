package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;

public class ButtonsObserver implements PropertyChangeListener {

	DrawingFrame frame;
	
	public ButtonsObserver(DrawingFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("btnSelect")) {
			this.frame.getTglbtnSelect().setEnabled((boolean)evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnModify")) {
			this.frame.getBtnModify().setEnabled((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnDelete")) {
			this.frame.getBtnDelete().setEnabled((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnUndo")) {
			this.frame.getBtnUndo().setEnabled((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnRedo")) {
			this.frame.getBtnRedo().setEnabled((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnToFront")) {
			this.frame.getBtnToFront().setEnabled((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnToBack")) {
			this.frame.getBtnToBack().setEnabled((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnBringToFront")) {
			this.frame.getBtnBringToFront().setEnabled((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equals("btnBringToBack")) {
			this.frame.getBtnBringToBack().setEnabled((boolean) evt.getNewValue());
		}
		
	}

}
