package userInterface;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

import soundDiagram.Optiums;


class DrawDiagram extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graphics g;
    private BufferStrategy bs;
    private byte []buff;
    
    DrawDiagram() {
		// TODO Auto-generated constructor stub
    	buff = new byte[800];
	}
	void makeCanvas(){
    	bs = getBufferStrategy(); 
	    if (bs == null) {
	        createBufferStrategy(2); 
	        requestFocus();
	        bs = getBufferStrategy(); 
	    }
	    g = bs.getDrawGraphics(); 
	    g.setColor(Color.white); 
	    g.fillRect(0, 0, this.getWidth(), this.getHeight()); 
	}
	
	void refresh(byte []diagramBuff){
		g.setColor(Color.black);
		buff = Arrays.copyOfRange(buff, Optiums.DIAGRAM_BUFF, 820);
		for(int i = 799 - Optiums.DIAGRAM_BUFF; i < 799; ++i){
			buff[i] = diagramBuff[i - 799 + Optiums.DIAGRAM_BUFF];
		}
		for(int i = 0; i < 799; ++i){
			g.drawLine(i + 50, this.getHeight() / 2, i + 50, this.getHeight() / 2 + buff[i]);
		}
		g.drawLine(799 + 50, this.getHeight() / 2, 799 + 50, this.getHeight() / 2 + buff[799]);
	    g.dispose();
	    bs.show(); 
	}
}
