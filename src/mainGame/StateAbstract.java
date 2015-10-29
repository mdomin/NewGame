package mainGame;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public abstract class StateAbstract implements StateInterface{
	
	boolean gameEnded;
	boolean gamePaused;

    public StateAbstract(JPanel panel) {
    	gameEnded = false;
    	gamePaused = false;
		keySetup(panel);
	}
    
    public void togglePause() {
    	gamePaused = !gamePaused;
    }
    
    public void setPaused() {
    	gamePaused = true;
    }
    
    public void setUnpaused() {
    	gamePaused = false;
    }
    
    @Override
    public boolean checkGamePaused() {
    	return gamePaused;
    }
    
    public void endGame() {
    	gameEnded = false;
    }
    
    @Override
    public boolean checkGameEnded() {
    	return gameEnded;
    }

	public static void bind(JComponent component, KeyStroke key, String string, Action action) {
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, string);
        component.getActionMap().put(string, action);
    }
    
}
