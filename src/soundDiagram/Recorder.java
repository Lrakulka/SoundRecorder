package soundDiagram;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import userInterface.Interface;

public class Recorder extends Thread implements StopThread{

    private TargetDataLine        m_line;
    private AudioFileFormat.Type    m_targetType;
    private AudioInputStream    m_audioInputStream;
    private Interface interf;
    private OutputStream outpuStream;
    private FileOutputStream recordFile;
    private byte []buff;
    
    public Recorder(TargetDataLine m_line, Type m_targetType) {
        this.m_line = m_line;
        this.m_targetType = m_targetType;
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
        										m_targetType.toString().toLowerCase()));
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
            AudioSystem.write(
                m_audioInputStream,
                m_targetType, outpuStream);
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