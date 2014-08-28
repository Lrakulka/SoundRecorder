package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import soundDiagram.Optiums;
import soundDiagram.Player;

// Action Listener for button Player
public class MyActionListenerForPlayer implements ActionListener{
	private Interface interf;
	private JButton button, buttonRecorder;
	private Player player;
    private JTextField edit;
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(button.getText().equals("Stop Play Record")){
			player.stopThread();
			interf.deleteMyWindowListener();
			button.setText("Start Play Record");
			if(Optiums.DISABLES_BUTTONS)
				buttonRecorder.setEnabled(true);
		}else{		
			if(Optiums.DISABLES_BUTTONS)
				buttonRecorder.setEnabled(false);
			File fileDir = new File(edit.getText() +"." +  Optiums.FILE_TYPE.toString().toLowerCase());
			if(fileDir.exists()){
				try {
					player = new Player(fileDir, button, buttonRecorder);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					edit.setText("Something go wrong.");
					if(Optiums.DISABLES_BUTTONS)
						buttonRecorder.setEnabled(true);
				}
				player.start(interf.DataSourthForDiagram(player));
				button.setText("Stop Play Record");
			}else{
				edit.setText("Wrong directory.");	
				if(Optiums.DISABLES_BUTTONS)
					buttonRecorder.setEnabled(true);
			}
		}
	}
	
	public MyActionListenerForPlayer(Interface interf, JButton button, JTextField edit, JButton buttonRecorder) {
		// TODO Auto-generated constructor stub
		this.edit = edit;
		this.interf = interf;
		this.button = button;
		this.buttonRecorder = buttonRecorder;
	}
}
