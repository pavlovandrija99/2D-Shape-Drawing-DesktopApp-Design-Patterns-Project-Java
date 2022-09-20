package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mvc.DrawingFrame;

public class SaveLog implements Save {

	
	private DrawingFrame frame;
	
	@Override
	public void save(Object o, File f) {
		this.frame = (DrawingFrame) o;
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter((new FileWriter(f.getAbsolutePath())));
            this.frame.getTextArea().write(bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
		
}

