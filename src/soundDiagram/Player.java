package soundDiagram;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;

import userInterface.Interface;


public class Player extends Thread implements StopThread{
	private Interface interf;
	private JButton button;
	private SourceDataLine line = null; 
	private AudioInputStream ais = null;
	private byte[] buff;
	private Info info;
	private AudioFormat af;
	
	public Player(File fileDir, JButton button) throws Exception {
		// TODO Auto-generated constructor stub
		this.button = button;
		buff = new byte[Optiums.BUFF_SIZE];
		ais = AudioSystem.getAudioInputStream(fileDir);
		af = ais.getFormat () ;
		info = new Info(SourceDataLine.class, af);
		if (!AudioSystem.isLineSupported(info)){
			throw new Exception("Line is not supported");
		}
	}
	
	 public void start(Interface interf){
	    	this.interf = interf;
	    	super.start();
	    }
	 
	public void run(){
		try{
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(af);
			line.start(); 
			int num = 0;
			while((ais != null) && ( num = ais.read(buff)) != -1){
				{
					interf.updateDiagram(buff);
					line.write(buff, 0, num);
				}
			}
			line.drain();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(ais != null){
			stopThread();		
		    button.setText("Start Recording Sound");
		    interf.deleteMyWindowListener();
		}
	}
	
	@Override
	public void stopThread() {
		// TODO Auto-generated method stub
		try {
			ais.close();
			ais = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		line.stop();
		line.close(); 
	}
}
