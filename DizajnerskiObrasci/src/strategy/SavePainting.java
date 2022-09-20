package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import mvc.DrawingModel;

public class SavePainting implements Save {

	DrawingModel model;
	
	@Override
	public void save(Object o, File f) {
		this.model = (DrawingModel) o;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));
            objectOutputStream.writeObject(this.model.getShapeList());
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
