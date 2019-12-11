package _08final.mvc.view;

import _08final.mvc.controller.Game;
import _08final.mvc.model.CommandCenter;
import _08final.mvc.model.Falcon;
import _08final.mvc.model.Movable;

import java.awt.*;
import java.util.ArrayList;


public class GamePanel extends Panel {
	
	// ==============================================================
	// FIELDS 
	// ============================================================== 
	 
	// The following "off" vars are used for the off-screen double-bufferred image. 
	private Dimension dimOff;
	private Image imgOff;
	private Graphics grpOff;
	
	private GameFrame gmf;
	private Font fnt = new Font("SansSerif", Font.BOLD, 12);
	private Font fntBig = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
	private FontMetrics fmt; 
	private int nFontWidth;
	private int nFontHeight;
	private String strDisplay = "";
	private boolean dontKnowEndTime;
	private long endTime;
	

	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	
	public GamePanel(Dimension dim){
	    gmf = new GameFrame();
		gmf.getContentPane().add(this);
		gmf.pack();
		initView();
		
		gmf.setSize(dim);
		gmf.setTitle("Game Base");
		gmf.setResizable(false);
		gmf.setVisible(true);
		this.setFocusable(true);
		dontKnowEndTime = true;
		endTime = 0;
	}
	
	
	// ==============================================================
	// METHODS 
	// ==============================================================
	
	private void drawScore(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		if (CommandCenter.getInstance().getScore() != 0) {
			g.drawString("SCORE :  " + CommandCenter.getInstance().getScore(), nFontWidth, nFontHeight);
		} else {
			g.drawString("NO SCORE", nFontWidth, nFontHeight);
		}
	}

	private void drawLevel(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		g.drawString("LEVEL :  " + CommandCenter.getInstance().getLevel(), 6 * nFontWidth, nFontHeight);

	}

	private void drawElapsedTime(Graphics g) {
		if (CommandCenter.getInstance().isPlaying()) {
			g.setColor(Color.white);
			g.setFont(fnt);
			g.drawString("Elapsed Time :  " + CommandCenter.getInstance().getElapsedTime() / 1000  / 60 + " : " +
												CommandCenter.getInstance().getElapsedTime() / 1000 % 60 + " : " +
												CommandCenter.getInstance().getElapsedTime() % 1000,
												10 * nFontWidth, nFontHeight);
		} else {
			g.setColor(Color.white);
			g.setFont(fnt);
			g.drawString("Elapsed Time :  " + "00 : 00 : 000", 10 * nFontWidth, nFontHeight);
		}

	}

	private void drawTimeLeft(Graphics g) {
		if (CommandCenter.getInstance().isPlaying()) {
			g.setColor(Color.white);
			g.setFont(fnt);
			g.drawString("Time Left :  " + (CommandCenter.getInstance().getGameTime() - CommandCenter.getInstance().getElapsedTime()) / 1000 / 60 + " : " +
							(CommandCenter.getInstance().getGameTime() - CommandCenter.getInstance().getElapsedTime()) / 1000 % 60 + " : " +
							(1000 - CommandCenter.getInstance().getElapsedTime() % 1000),
					19 * nFontWidth, nFontHeight);
		} else {
			g.setColor(Color.white);
			g.setFont(fnt);
			g.drawString("Time Left :  " + (CommandCenter.getInstance().getGameTime() / 1000 / 60) + " : " +
											(CommandCenter.getInstance().getGameTime() / 1000 % 60) + " : " +
											(CommandCenter.getInstance().getGameTime() % 1000), 19 * nFontWidth, nFontHeight);
		}

	}

	private void drawCruiseShotsLeft(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		if (CommandCenter.getInstance().isPlaying()) {
			g.drawString("Cruise Shots left : " + CommandCenter.getInstance().getFalcon().getCruiseShots(), 27 * nFontWidth, nFontHeight);
		} else {
			g.drawString("Cruise Shots left : 5", 27 * nFontWidth, nFontHeight);
		}
	}

	private void drawBloomingShotsLeft(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		if (CommandCenter.getInstance().isPlaying()) {
			g.drawString("Blooming Shots left : " + CommandCenter.getInstance().getFalcon().getBloomingShots(), 34 * nFontWidth, nFontHeight);
		} else {
			g.drawString("Blooming Shots left : 10", 34 * nFontWidth, nFontHeight);

		}
	}

	private void drawShieldsLeft(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		if (CommandCenter.getInstance().isPlaying()) {
			g.drawString("Shields left : " + CommandCenter.getInstance().getFalcon().getnShield(), 42 * nFontWidth, nFontHeight);
		} else {
			g.drawString("Shields left : 1", 42 * nFontWidth, nFontHeight);

		}
	}

	private void drawHyperLeft(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		if (CommandCenter.getInstance().isPlaying()) {
			g.drawString("Hypers left : " + CommandCenter.getInstance().getFalcon().getnHyper(), 47 * nFontWidth, nFontHeight);
		} else {
			g.drawString("Hypers left : 1", 47 * nFontWidth, nFontHeight);

		}
	}
	
	@SuppressWarnings("unchecked")
	public void update(Graphics g) {
		if (grpOff == null || Game.DIM.width != dimOff.width
				|| Game.DIM.height != dimOff.height) {
			dimOff = Game.DIM;
			imgOff = createImage(Game.DIM.width, Game.DIM.height);
			grpOff = imgOff.getGraphics();
		}
		// Fill in background with black.
		grpOff.setColor(Color.black);
		grpOff.fillRect(0, 0, Game.DIM.width, Game.DIM.height);


		drawScore(grpOff);
		drawLevel(grpOff);
		drawElapsedTime(grpOff);
		drawTimeLeft(grpOff);
		drawCruiseShotsLeft(grpOff);
		drawBloomingShotsLeft(grpOff);
		drawShieldsLeft(grpOff);
		drawHyperLeft(grpOff);


		if (!CommandCenter.getInstance().isPlaying()) {
			displayTextOnScreen();
		} else if (CommandCenter.getInstance().isPaused()) {
			strDisplay = "Game Paused";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);
		}
		
		//playing and not paused!
		else {
			
			//draw them in decreasing level of importance
			//friends will be on top layer and debris on the bottom
			iterateMovables(grpOff,
					(ArrayList<Movable>)  CommandCenter.getInstance().getMovFriends(),
					(ArrayList<Movable>)  CommandCenter.getInstance().getMovFoes(),
					(ArrayList<Movable>)  CommandCenter.getInstance().getMovFloaters(),
					(ArrayList<Movable>)  CommandCenter.getInstance().getMovDebris());


			drawNumberShipsLeft(grpOff);
			if (CommandCenter.getInstance().isGameOver()) {
				CommandCenter.getInstance().setPlaying(false);
				//bPlaying = false;
			}
		}
		//draw the double-Buffered Image to the graphics context of the panel
		g.drawImage(imgOff, 0, 0, this);
	} 


	
	//for each movable array, process it.
	private void iterateMovables(Graphics g, ArrayList<Movable>...movMovz){
		
		for (ArrayList<Movable> movMovs : movMovz) {
			for (Movable mov : movMovs) {

				mov.move();
				mov.draw(g);

			}
		}
		
	}
	

	// Draw the number of falcons left on the bottom-right of the screen. 
	private void drawNumberShipsLeft(Graphics g) {
		Falcon fal = CommandCenter.getInstance().getFalcon();
		double[] dLens = fal.getLengths();
		int nLen = fal.getDegrees().length;
		Point[] pntMs = new Point[nLen];
		int[] nXs = new int[nLen];
		int[] nYs = new int[nLen];
	
		//convert to cartesean points
		for (int nC = 0; nC < nLen; nC++) {
			pntMs[nC] = new Point((int) (10 * dLens[nC] * Math.sin(Math
					.toRadians(90) + fal.getDegrees()[nC])),
					(int) (10 * dLens[nC] * Math.cos(Math.toRadians(90)
							+ fal.getDegrees()[nC])));
		}
		
		//set the color to white
		g.setColor(Color.white);
		//for each falcon left (not including the one that is playing)
		for (int nD = 1; nD < CommandCenter.getInstance().getNumFalcons(); nD++) {
			//create x and y values for the objects to the bottom right using cartesean points again
			for (int nC = 0; nC < fal.getDegrees().length; nC++) {
				nXs[nC] = pntMs[nC].x + Game.DIM.width - (20 * nD);
				nYs[nC] = pntMs[nC].y + Game.DIM.height - 40;
			}
			g.drawPolygon(nXs, nYs, nLen);
		} 
	}
	
	private void initView() {
		Graphics g = getGraphics();			// get the graphics context for the panel
		g.setFont(fnt);						// take care of some simple font stuff
		fmt = g.getFontMetrics();
		nFontWidth = fmt.getMaxAdvance();
		nFontHeight = fmt.getHeight();
		g.setFont(fntBig);					// set font info
	}
	
	// This method draws some text to the middle of the screen before/after a game
	private void displayTextOnScreen() {

		if (CommandCenter.getInstance().getGameInitiated()) {
			strDisplay = "GAME OVER";
			if (CommandCenter.getInstance().getGameCleared()) {
				strDisplay = "Congratulations! You cleared all levels!";
			} else if (CommandCenter.getInstance().getGameTimedOut()) {
				strDisplay += "\n You ran out of time!";
			} else if (CommandCenter.getInstance().getGameKilled()) {
				strDisplay += " You ran out of lives!";
			}
		} else {
			strDisplay = "Welcome! \n Please review the following game instructions";
		}
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

		if (!CommandCenter.getInstance().getGameInitiated()) {
			strDisplay = "use the arrow keys to turn and thrust";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 40);

			strDisplay = "use the space bar to fire";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 80);

			strDisplay = "'S' to Start";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 120);

			strDisplay = "'P' to Pause";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 160);

			strDisplay = "'Q' to Quit";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 200);
			strDisplay = "left pinkie on 'A' for Shield";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 240);

			strDisplay = "left index finger on 'F' for Guided Missile";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 280);

			strDisplay = "'Numeric-Enter' for Hyperspace";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 320);
		} else {
			if (dontKnowEndTime) {
				endTime = System.currentTimeMillis();
				dontKnowEndTime = false;
			}
			strDisplay = "Game Statistics:";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
								+ nFontHeight + 40);
			if (CommandCenter.getInstance().getLevel() == 0) {
				strDisplay = "Levels Cleared: " + (0);
			} else {
				strDisplay = "Levels Cleared: " + (CommandCenter.getInstance().getLevel() - 1);
			}

			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 80);

			strDisplay = "Total Score: " + CommandCenter.getInstance().getScore();
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 120);

			strDisplay = "'S' to re-Start";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 200);
			strDisplay = "'Q' to Quit";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 240);

			long timeElapsed = Math.abs(endTime - CommandCenter.getInstance().getGameEpochTime() );
			int minutes = (int)  timeElapsed / 1000 / 60;
			int seconds = (int) timeElapsed / 1000 % 60;
			int miliSeconds = (int) timeElapsed % 1000;
			strDisplay = "Total Time Elapsed: " + minutes + " : " + seconds + " : " + miliSeconds;
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
							+ nFontHeight + 160);

		}
	}
	
	public GameFrame getFrm() {return this.gmf;}
	public void setFrm(GameFrame frm) {this.gmf = frm;}	
}