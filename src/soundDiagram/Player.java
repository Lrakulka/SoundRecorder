package soundDiagram;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;

import userInterface.Interface;


public class Player extends Thread implements StopThread{
	private File fileDir;
	private Interface interf;
	private Clip clip;
	private JButton button;
	
	public Player(File fileDir, JButton button) {
		// TODO Auto-generated constructor stub
		this.fileDir = fileDir;
		this.button = button;
	}
	
	 public void start(Interface interf){
	    	this.interf = interf;
	       // super.start();
	    	super.start();
	    }
	 
	public void run(){
		try{
		    
		    //�������� AudioInputStream
		    //��� ��� ����� �������� IOException � UnsupportedAudioFileException
		    AudioInputStream ais = AudioSystem.getAudioInputStream(fileDir);
		    
		    //�������� ���������� ���������� Clip
		    //����� �������� LineUnavailableException
		    clip = AudioSystem.getClip();
		    
		    //��������� ��� �������� ����� � Clip
		    //����� �������� IOException � LineUnavailableException
		    clip.open(ais);
		    clip.setFramePosition(0); //������������� ��������� �� �����
		    clip.start(); //�������!!!
		
		    //���� �� �������� ������ �������, �� ����� ���������, ���� ���� �� ����������
		        //� GUI-����������� ��������� 3 ������� �� �����������
		    Thread.sleep(clip.getMicrosecondLength()/1000);
		    stopThread();
		    button.setText("Start Recording Sound");
		    interf.deleteMyWindowListener();
		   // clip.stop(); //�������������
		   // clip.close(); //���������
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
		    exc.printStackTrace();
		} catch (InterruptedException exc) {}
	}

	@Override
	public void stopThread() {
		// TODO Auto-generated method stub
		clip.stop(); //�������������
	    clip.close(); //���������
	}
}
