package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import collision.CollisionDetector;


@SuppressWarnings("serial")
public class Framework extends JPanel {
	
	// DEBUG
	private static int totalFrames = 0;
	private static double totalElapsedTime = 0;
	private static List<Double> lastFewTimes;
	private static final int framesSaved = 50;

	private double interpolation;

	
	public static int GAMEWIDTH  = (int) ( 800 * 1.5 );
	public static int GAMEHEIGHT = (int) ( 640 * 1 );
	public static int numObjects = 10000;
		
	private static int UPDATES_PER_SEC = 25;  // Number of game update per second
	private static int REPAINTS_PER_SEC = 60; // Number of frames per second
	private static double timeBeforeUpdate = 1000.0 / UPDATES_PER_SEC; // ticks per second;
	private static double timeBeforeRepaint = 1000.0 / REPAINTS_PER_SEC;

	
	//private static long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SEC;  // nanoseconds
	private static boolean LIMITFPS = true;
	private static final String TITLE = "Collision Testing";
	
		
	private StateInterface gameState;
	private double fps;
	private long timeTaken;
	public boolean paused;
	
	public Framework() {		
		// Setup
		super();
		paused = false;
		gameState = setCombat();
		keySetup(this);		
		
		
		// DEBUG
		lastFewTimes = new ArrayList<Double>();
		for(int i = 0; i < framesSaved; i++) {lastFewTimes.add(0.0);}

		gameStart();
	}
	
	private StateInterface setCombat() {
		// Statistics
		StateInterface temp = new CollisionDetector(this, GAMEWIDTH, GAMEHEIGHT);
		return temp;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameState.doGraphics(g2d, interpolation);
        
		double avg = 0;
		int size = 0;
		if (!gameState.checkGamePaused()) {
			totalFrames++;
			totalElapsedTime += (timeTaken / 1000000);
			lastFewTimes.remove(0);
			lastFewTimes.add((double) (timeTaken / 1000000));
			
			size = lastFewTimes.size();
			for(int i = 0; i < size; i++) {
				avg += lastFewTimes.get(i) / size;
			}
		}
		
		g2d.setColor(Color.white);
		g2d.drawString("FPS: " + Double.toString(fps) + " (" + Integer.toString(REPAINTS_PER_SEC) + ")", 10, 15);
		g2d.drawString("Elapsed Time: " + Double.toString(timeTaken / 1000000) + " ms", 10, 30);
		g2d.drawString("Average Frame Time: " + String.format("%.3f", totalElapsedTime / totalFrames) + " ms (" + 
				Integer.toString(totalFrames) + " frames)" , 10, 45);
		g2d.drawString("Average Frame Time for " + framesSaved + " Frames: " + String.format("%.3f", avg), 10, 60);
	}
	
	
	/** Transform (rotation)
		AffineTransform old = g2d.getTransform();
		g2d.rotate(obj.getAngleRad(), obj.getX(), obj.getY());
		g2d.setTransform(old);
	 */
	
	public void gameStart() {
		Thread gameThread = new Thread() {
			@Override
			public void run() {
				gameLoop();
			}
		};
		gameThread.start();
	}
	
	private void update() {
		if(!gameState.checkGamePaused()) {
			gameState.update();
			gameState.checkDeletion();
		}
	}
	
	public void gameLoop() {
				
		boolean gameRunning = true;		
		long previousUpdateTime, previousRepaintTime, currentTime, timeTakenUpdate, timeTakenRepaint;
		previousUpdateTime = System.nanoTime();
		previousRepaintTime = previousUpdateTime;

		while (gameRunning) {
			currentTime = System.nanoTime();
			timeTakenUpdate = (currentTime - previousUpdateTime) / 1000000;
			if (timeTakenUpdate >= timeBeforeUpdate) {
				update();
				previousUpdateTime = currentTime;
			}
			timeTakenRepaint = (currentTime - previousRepaintTime)  / 1000000;
			if (timeTakenRepaint >= timeBeforeRepaint) {
				interpolation = Math.min(timeTakenUpdate / timeBeforeUpdate, 1);
				repaint();
				previousRepaintTime = currentTime;
			}
		}
		
		/**
		long previousTime, currentTime;
		previousTime = System.nanoTime();
		
		boolean gameRunning = true;
		
		while(gameRunning) {
			currentTime = System.nanoTime();
		}
		
		
		
		long beginTime, timeLeft;
		while(true) {
			while (!gameState.checkGameEnded()) {
				beginTime = System.nanoTime();
				
				if(!gameState.checkGamePaused()) {
					gameState.update();
					gameState.checkDeletion();
				}
				this.repaint();
				

				timeTaken = System.nanoTime() - beginTime;
				
				if(LIMITFPS) {
					fps = Math.round(1000 / Math.max((double)1000/UPDATES_PER_SEC, (timeTaken / 1000000)));
					timeLeft = (UPDATE_PERIOD_NSEC - timeTaken) / 1000000;
					if (timeLeft < 10)
						timeLeft = 10;   // set a minimum
				}
				else {
					fps = Math.round(1000 / Math.max(0.00001, (timeTaken / 1000000)));
					timeLeft = 10;
				}

				try {
					Thread.sleep(timeLeft);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				// koonsolo.com
				// for information on constant game speed + variable fps
						
			}
		}
		**/
	}
	
	public void keySetup(JPanel panel) {
		StateAbstract.bind(panel, KeyStroke.getKeyStroke("OPEN_BRACKET"), "fps decrease", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (REPAINTS_PER_SEC > 1)
					timeBeforeRepaint = 1000.0 / REPAINTS_PER_SEC--;					
			}
		});
		
		StateAbstract.bind(panel, KeyStroke.getKeyStroke("CLOSE_BRACKET"), "fps increase", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timeBeforeRepaint = 1000.0 / REPAINTS_PER_SEC++;
			}
		});
		
		StateAbstract.bind(panel, KeyStroke.getKeyStroke("BACK_SLASH"), "toggle fps limit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LIMITFPS = !LIMITFPS;
			}
		});
	}
	
    public static void main(String[] args) {
		Framework gameFrame = new Framework();
		
		JFrame frame = new JFrame(TITLE);
		frame.add(gameFrame);
		frame.setSize(GAMEWIDTH, GAMEHEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
