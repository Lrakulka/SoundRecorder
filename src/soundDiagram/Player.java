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
		    
		    //Получаем AudioInputStream
		    //Вот тут могут полететь IOException и UnsupportedAudioFileException
		    AudioInputStream ais = AudioSystem.getAudioInputStream(fileDir);
		    
		    //Получаем реализацию интерфейса Clip
		    //Может выкинуть LineUnavailableException
		    clip = AudioSystem.getClip();
		    
		    //Загружаем наш звуковой поток в Clip
		    //Может выкинуть IOException и LineUnavailableException
		    clip.open(ais);
		    clip.setFramePosition(0); //устанавливаем указатель на старт
		    clip.start(); //Поехали!!!
		
		    //Если не запущено других потоков, то стоит подождать, пока клип не закончится
		        //В GUI-приложениях следующие 3 строчки не понадобятся
		    Thread.sleep(clip.getMicrosecondLength()/1000);
		    stopThread();
		    button.setText("Start Recording Sound");
		    interf.deleteMyWindowListener();
		   // clip.stop(); //Останавливаем
		   // clip.close(); //Закрываем
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
		    exc.printStackTrace();
		} catch (InterruptedException exc) {}
	}

	@Override
	public void stopThread() {
		// TODO Auto-generated method stub
		clip.stop(); //Останавливаем
	    clip.close(); //Закрываем
	}
}
