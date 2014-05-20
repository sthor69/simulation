package com.storassa.java.simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SpringDraw extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Spring[][] s;
	private int numCol;

	public SpringDraw(Spring[][] _s, int _numCol) {
		s = _s;
		numCol = _numCol;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		boolean redY = true;
		for (int i = 0; i < numCol - 1; i++) {
			boolean redX = redY;
			for (int t = 0; t < numCol - 1; t++) {
				// if (s[i][t] != null) {
				// Particle p1 = s[i][t].getParticles()[0];
				// Particle p2 = s[i][t].getParticles()[1];
				// Line2D line = new Line2D.Double(p1.getPosition()[0],
				// p1.getPosition()[1], p2.getPosition()[0],
				// p2.getPosition()[1]);
				// g2.draw(line);
				// }
				int nPoints = 4;
				int[] xPoints = new int[nPoints + 1];
				int[] yPoints = new int[nPoints + 1];

				Particle p0 = s[t + i * numCol][t + i * numCol + 1]
						.getParticles()[0];
				Particle p1 = s[t + i * numCol][t + i * numCol + 1]
						.getParticles()[1];
				Particle p2 = s[t + (i + 1) * numCol + 1][t + (i + 1) * numCol]
						.getParticles()[0];
				Particle p3 = s[t + (i + 1) * numCol + 1][t + (i + 1) * numCol]
						.getParticles()[1];

				xPoints[0] = (int) p0.getPosition()[0];
				xPoints[1] = (int) p1.getPosition()[0];
				xPoints[2] = (int) p2.getPosition()[0];
				xPoints[3] = (int) p3.getPosition()[0];

				yPoints[0] = (int) p0.getPosition()[1];
				yPoints[1] = (int) p1.getPosition()[1];
				yPoints[2] = (int) p2.getPosition()[1];
				yPoints[3] = (int) p3.getPosition()[1];
				if (redX)
					g2.setColor(new Color(255, 0, 0));
				else
					g2.setColor(new Color(0, 0, 255));
				g2.fillPolygon(xPoints, yPoints, nPoints);

				redX = !redX;
			}
			redY = !redY;
		}

	}

}
