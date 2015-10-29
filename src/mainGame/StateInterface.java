package mainGame;

import java.awt.Graphics2D;

import javax.swing.JPanel;

public interface StateInterface {
	
	public void update();
	public void checkDeletion();
	
	abstract boolean checkGamePaused();
	
	abstract boolean checkGameEnded();
	
	public void keySetup(JPanel panel);
	
	public void doGraphics(Graphics2D g2d, double interpolation);
}
