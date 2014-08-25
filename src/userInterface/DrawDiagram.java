package userInterface;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


class DrawDiagram extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graphics g;
    private BufferStrategy bs;
    private int []buff;
    
    DrawDiagram() {
		// TODO Auto-generated constructor stub
    	buff = new int[800];
	}
	void makeCanvas(){
    	bs = getBufferStrategy(); 
	    if (bs == null) {
	        createBufferStrategy(2); //создаем BufferStrategy для нашего холста
	        requestFocus();
	        bs = getBufferStrategy(); 
	    }
	    g = bs.getDrawGraphics(); 
	    g.setColor(Color.white); 
	    g.fillRect(0, 0, this.getWidth(), this.getHeight()); 
	}
	
	void refresh(int arg){
		g.setColor(Color.black);
		for(int i = 0; i < 799; ++i){
			buff[i] = buff[i+1];
			g.drawLine(i + 50, this.getHeight() / 2, i + 50, this.getHeight() / 2 + buff[i]);
		}
		buff[799] = arg;
		g.drawLine(799 + 50, this.getHeight() / 2, 799 + 50, this.getHeight() / 2 + buff[799]);
	    g.dispose();
	    bs.show(); 
	}
}
