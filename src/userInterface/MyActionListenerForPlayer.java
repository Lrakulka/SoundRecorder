package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import soundDiagram.Optiums;
import soundDiagram.Player;

public class MyActionListenerForPlayer implements ActionListener{
	private Interface interf;
	private JButton button;
	private Player player;
    private JTextField edit;
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(button.getText().equals("Stop Play Record")){
			player.stopThread();
			interf.deleteMyWindowListener();
			button.setText("Start Play Record");
		}else{			
			File fileDir = new File(edit.getText() +"." +  Optiums.FILE_TYPE.toString().toLowerCase());
			if(fileDir.isFile()){
				player = new Player(fileDir, button);
				player.start(interf);
				button.setText("Stop Play Record");
			}else{
				edit.setText("Wrong directory.");	
			}
		}
	}
	
	public MyActionListenerForPlayer(Interface interf, JButton button, JTextField edit) {
		// TODO Auto-generated constructor stub
		this.edit = edit;
		this.interf = interf;
		this.button = button;
	}
}
