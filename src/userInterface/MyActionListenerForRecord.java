package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
		if(button.getText().equals("Stop Recording")){
			recorder.stopThread();
			interf.deleteMyWindowListener();
			button.setText("Start Recording");
			if(Optiums.DISABLES_BUTTONS)
				buttonPlayer.setEnabled(true);
		}else{			
			if(Optiums.DISABLES_BUTTONS)
				buttonPlayer.setEnabled(false);
			AudioFormat audioFormat = new AudioFormat(   // http://pubs.opengroup.org/external/auformat.html
	        		Optiums.ENCODING, Optiums.SAMPLE_RATE, 
	                Optiums.SAMPLE_SIZE_IN_BITS, Optiums.CHENNELS, Optiums.FRAME_SIZE,
	                Optiums.FRAME_RATE, Optiums.BIG_ENDIAN);
			File f = new File(edit.getText() + "." + Optiums.FILE_TYPE.toString().toLowerCase());
			if(!f.exists())
				try{
					f.createNewFile();
					Optiums.FILE_RECORD_NAME = edit.getText();
				}
				catch (Exception e){
					try{
						if(!f.getParentFile().exists() && (f.getParentFile().mkdirs())){
								f.createNewFile();
								Optiums.FILE_RECORD_NAME = edit.getText();
						}else throw new Exception();										
					}
					catch (Exception e2) {
						// TODO Auto-generated catch block
						Optiums.FILE_RECORD_NAME += "#";
						edit.setText("Wrong directory. Record to file <" + Optiums.FILE_RECORD_NAME + ">.");
						//e.printStackTrace();
					}
				}
			else Optiums.FILE_RECORD_NAME = edit.getText();
	        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
	        TargetDataLine targetDataLine = null;
	        try
	        {
	            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
	            targetDataLine.open(audioFormat);
	        }
	        catch (LineUnavailableException e3)
	        {
	            edit.setText("Unable to get a recording line");
	            if(Optiums.DISABLES_BUTTONS)
					buttonPlayer.setEnabled(true);
	            //e.printStackTrace();
	        }

			recorder = new Recorder(targetDataLine);			
			recorder.start(interf.DataSourthForDiagram(recorder));  //Bind recorder with Interface
			button.setText("Stop Recording");
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
