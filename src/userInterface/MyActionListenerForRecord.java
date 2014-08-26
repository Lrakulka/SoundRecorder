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


// Action Listener for button Record
class MyActionListenerForRecord implements ActionListener {
	private Interface interf;
	private JButton button, buttonPlayer;
	private Recorder recorder;
    private JTextField edit;
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(button.getText().equals("Stop Recording Sound")){
			recorder.stopThread();
			interf.deleteMyWindowListener();
			button.setText("Start Recording Sound");
			if(Optiums.DISABLES_BUTTONS)
				buttonPlayer.setEnabled(true);
		}else{			
			if(Optiums.DISABLES_BUTTONS)
				buttonPlayer.setEnabled(false);
			AudioFormat audioFormat = new AudioFormat(   // http://pubs.opengroup.org/external/auformat.html
	        		Optiums.ENCODING, Optiums.SAMPLE_RATE, 
	                Optiums.SAMPLE_SIZE_IN_BITS, Optiums.CHENNELS, Optiums.FRAME_SIZE,
	                Optiums.FRAME_RATE, Optiums.BIG_ENDIAN);
			File f = new File(edit.getText() + "." + Optiums.FILE_TYPE);
			try{
				if(f.exists())
					if(f.getParentFile().exists()){
						if(f.getParentFile().mkdirs())
							f.createNewFile();
						else throw new Exception();
					}
					else f.createNewFile();
				else throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Optiums.FILE_RECORD_NAME += "!";
				edit.setText("Wrong directory. Record to " + Optiums.FILE_RECORD_NAME);	
				e.printStackTrace();
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
	            edit.setText("Unable to get a recording line");
	            e.printStackTrace();
	        }

	        AudioFileFormat.Type targetType = Optiums.FILE_TYPE;
			recorder = new Recorder(targetDataLine,targetType);			
			recorder.start(interf.DataSourthForDiagram(recorder));  //Bind recorder with Interface
			button.setText("Stop Recording Sound");
		}
	}
	
	public MyActionListenerForRecord(Interface interf, JButton button, JTextField edit, JButton buttonPlayer) {
		// TODO Auto-generated constructor stub
		this.edit = edit;
		this.interf = interf;
		this.button = button;
		this.buttonPlayer = buttonPlayer;
	}
}
