package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JTextField;

import soundDiagram.Optiums;
import soundDiagram.Recorder;



class MyActionListenerForRecord implements ActionListener {
	private Interface interf;
	private JButton button;
	private Recorder recorder;
    private JTextField edit;
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(button.getText().equals("Stop Recording Sound")){
			recorder.stopThread();
			interf.deleteMyWindowListener();
			button.setText("Start Recording Sound");
		}else{			
			AudioFormat audioFormat = new AudioFormat(   // http://pubs.opengroup.org/external/auformat.html
	        		Optiums.ENCODING, Optiums.SAMPLE_RATE, 
	                Optiums.SAMPLE_SIZE_IN_BITS, Optiums.CHENNELS, Optiums.FRAME_SIZE,
	                Optiums.FRAME_RATE, Optiums.BIG_ENDIAN);
			
				try {
					if(edit.getText().lastIndexOf("/") != -1)
						new File(edit.getText().substring(0 , edit.getText().lastIndexOf("/"))).mkdirs();
					new File(edit.getText() + Optiums.FILE_TYPE).createNewFile();     // Need fix stupid checking
					Optiums.FILE_RECORD_NAME = edit.getText();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Optiums.FILE_RECORD_NAME += "!";
					edit.setText("Wrong directory. Record to " + Optiums.FILE_RECORD_NAME);	
				}
					
	        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
	        TargetDataLine targetDataLine = null;
	        try
	        {
	            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
	            targetDataLine.open(audioFormat);
	        }
	        catch (LineUnavailableException e)
	        {
	            System.out.println("unable to get a recording line");
	            e.printStackTrace();
	            System.exit(1);
	        }

	        AudioFileFormat.Type targetType = Optiums.FILE_TYPE;
			recorder = new Recorder(targetDataLine,targetType);			
			recorder.start(interf.DataSourthForDiagram(recorder));  //
			button.setText("Stop Recording Sound");
		}
	}
	
	public MyActionListenerForRecord(Interface interf, JButton button, JTextField edit) {
		// TODO Auto-generated constructor stub
		this.edit = edit;
		this.interf = interf;
		this.button = button;
	}
}
