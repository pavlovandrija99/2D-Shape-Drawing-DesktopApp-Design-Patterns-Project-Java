package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableButtons {
	
	private boolean isBtnSelectEnabled, isBtnModifyEnabled, isBtnDeleteEnabled, isBtnUndoEnabled, 
					isBtnRedoEnabled, isBtnToFrontEnabled, isBtnToBackEnabled, isBtnBringToFronEnabled, 
					isBtnBringToBackEnabled;

	private PropertyChangeSupport propertyChangeSupport;
	
	
	public ObservableButtons() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public void setBtnSelectEnabled(boolean isBtnSelectEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnSelect", this.isBtnSelectEnabled, isBtnSelectEnabled);
		this.isBtnSelectEnabled = isBtnSelectEnabled;
	}

	public void setBtnModifyEnabled(boolean isBtnModifyEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnModify", this.isBtnModifyEnabled, isBtnModifyEnabled);
		this.isBtnModifyEnabled = isBtnModifyEnabled;
	}

	public void setBtnDeleteEnabled(boolean isBtnDeleteEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnDelete", this.isBtnDeleteEnabled, isBtnDeleteEnabled);
		this.isBtnDeleteEnabled = isBtnDeleteEnabled;
	}

	public void setBtnUndoEnabled(boolean isBtnUndoEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnUndo", this.isBtnUndoEnabled, isBtnUndoEnabled);
		this.isBtnUndoEnabled = isBtnUndoEnabled;
	}

	public void setBtnRedoEnabled(boolean isBtnRedoEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnRedo", this.isBtnRedoEnabled, isBtnRedoEnabled);
		this.isBtnRedoEnabled = isBtnRedoEnabled;
	}

	public void setBtnToFrontEnabled(boolean isBtnToFrontEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnToFront", this.isBtnToFrontEnabled, isBtnToFrontEnabled);
		this.isBtnToFrontEnabled = isBtnToFrontEnabled;
	}

	public void setBtnToBackEnabled(boolean isBtnToBackEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnToBack", this.isBtnToBackEnabled, isBtnToBackEnabled);
		this.isBtnToBackEnabled = isBtnToBackEnabled;
	}

	public void setBtnBringToFronEnabled(boolean isBtnBringToFronEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnBringToFront", this.isBtnBringToFronEnabled, isBtnBringToFronEnabled);
		this.isBtnBringToFronEnabled = isBtnBringToFronEnabled;
	}

	public void setBtnBringToBackEnabled(boolean isBtnBringToBackEnabled) {
		this.propertyChangeSupport.firePropertyChange("btnBringToBack", this.isBtnBringToBackEnabled, isBtnBringToBackEnabled);
		this.isBtnBringToBackEnabled = isBtnBringToBackEnabled;
	}

}




