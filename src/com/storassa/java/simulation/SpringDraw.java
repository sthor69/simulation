package com.storassa.java.simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SpringDraw extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Spring> s;
	private int numCol;

	public SpringDraw(ArrayList<Spring> _s, int _numCol) {
		s = _s;
		numCol = _numCol;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Spring spring : s) {
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

			Particle p0 = spring.getParticles()[0];
			Particle p1 = spring.getParticles()[1];

			xPoints[0] = (int) p0.getPosition()[0];
			xPoints[1] = (int) p1.getPosition()[0];

			yPoints[0] = (int) p0.getPosition()[1];
			yPoints[1] = (int) p1.getPosition()[1];
			g2.drawLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);

		}
	}

}
