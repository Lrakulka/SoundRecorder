package soundDiagram;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import userInterface.Interface;

public class Recorder extends Thread implements StopThread{

    private TargetDataLine m_line;
    private AudioInputStream m_audioInputStream;
    private Interface interf;
    private OutputStream outpuStream;
    private FileOutputStream recordFile;
    private byte []buff;
    
    public Recorder(TargetDataLine m_line) {
        this.m_line = m_line;
        this.m_audioInputStream = new AudioInputStream(m_line);
    }
    
    public void start(Interface interf){
    	this.interf = interf;
        m_line.start();
        super.start();
    }

    public void run(){  		
        try
        {
        	buff = new byte[Optiums.BUFF_SIZE];
        	recordFile = new FileOutputStream(new File(Optiums.FILE_RECORD_NAME + "." + 
        										Optiums.FILE_TYPE.toString().toLowerCase()));
        	outpuStream = new OutputStream() {
        		private byte l = 0;        
        		private int buffFill = 0;
        		@Override
				public void write(int arg0) throws IOException {
					// TODO Auto-generated method stub
        			if(m_line.isRunning()){
        				recordFile.write(arg0);
        				buff[buffFill] = (byte) arg0;
        				buffFill++;
        			}
        			if(buffFill == Optiums.BUFF_SIZE - 1){
        				 /* Work faster than class Player function update. 
        				That is why diagrams of same files can have little differences.*/
        				interf.updateDiagram(buff);
        				buffFill = 0;
        			}
					if(l < 24){					
	        			recordFile.write(arg0);  // Write magic numbers
	        			l++;
					}
				}
			};
            AudioSystem.write(m_audioInputStream, 
            		Optiums.FILE_TYPE, outpuStream); // There will be error if use the Wave format
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

	@Override
	public void stopThread() {
		// TODO Auto-generated method stub
		m_line.stop();
        m_line.close();
		try{		
			recordFile.close();
			outpuStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}