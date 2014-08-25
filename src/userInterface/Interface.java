package userInterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import soundDiagram.Optiums;
import soundDiagram.StopThread;


public class Interface extends Thread{
    private DrawDiagram drawDiagram;
    private JFrame frame;
    private boolean status;
    private int[] buff;
	private int lengthBuff;
	private int arg;
	private StopThread stopInputStream;
	private MyWindowListener myWindowListener;
	
    public Interface(){
    	lengthBuff = 0;
    	drawDiagram = new DrawDiagram();
    	drawDiagram.setPreferredSize(new Dimension(900, 400));    	
    	
    	frame = new JFrame("Sound Diagram");
    	frame.setPreferredSize(new Dimension(900, 500));       	  	
    	
    	JTextField edit = new JTextField("Write directory of record or play file");
		edit.setPreferredSize(new Dimension(200, 50));
		edit.setMargin(new Insets(5, 20, 5, 20));   
		
    	JButton buttonStartPlay = new JButton("Start Play Record");
    	buttonStartPlay.setPreferredSize(new Dimension(200, 50));    	
    	buttonStartPlay.addActionListener(new MyActionListenerForPlayer(this, buttonStartPlay, edit));
    	
    	JButton buttonRecordStar = new JButton("Start Record Sound");
    	buttonRecordStar.setPreferredSize(new Dimension(200, 50));
    	buttonRecordStar.addActionListener(new MyActionListenerForRecord(this, buttonRecordStar, edit));
    	
    	frame.add(drawDiagram, BorderLayout.NORTH);  
    	frame.add(buttonStartPlay, BorderLayout.EAST);
    	frame.add(buttonRecordStar, BorderLayout.WEST);	
    	frame.add(edit, BorderLayout.CENTER);
    	
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    frame.pack();
	    frame.setResizable(false); // No resizable for canvas
	    frame.setVisible(true);
    }
    
    @Override
    public void start(){
    	status = true;
    	super.start();
    }
    
	public void run(){		
	    for(;status;){		    	
	    	drawDiagram.makeCanvas();
	    	drawDiagram.refresh(getArgs());
	    	try {
				Thread.sleep(Optiums.THREAD_SLEEPING);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public void updateDiagram(int arg0){
		buff[lengthBuff] = arg0;
		lengthBuff++;
	}
	
	private int getArgs(){
		//-------//
		if(lengthBuff > 0)
			arg = buff[lengthBuff - 1];
		else arg = 0;
		lengthBuff = 0;
		//-------//
		return arg;
	}

	public Interface DataSourthForDiagram(StopThread stopInputStream){
    	int l = 1000 * Optiums.SAMPLE_RATE / Optiums.THREAD_SLEEPING;
    	buff = new int[(int) (l + l * 0.2)];
    	this.stopInputStream = stopInputStream;
    	myWindowListener = new MyWindowListener(this);
    	frame.addWindowListener(myWindowListener);
    	return this;
	}
	
	void closeAll(){
		stopInputStream.stopThread();
		status = false;
	}
	
	public void deleteMyWindowListener(){
		frame.removeWindowListener(myWindowListener);
	}
}
