package com.storassa.java.simulation;

import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawForce extends JPanel {

	private Particle p1, p2;
	private Spring s;

	public DrawForce(Spring _s, Particle _p1, Particle _p2) {
		p1 = new Particle(_p1);
		p2 = new Particle(_p2);
		s = _s;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawLine(0, 100, 200, 100);

		for (int i = 0; i < 200; i += 10)
			g.drawLine(i, 90, i, 110);

		Particle p = new Particle(p1);
		s = new Spring(p, p2, 2, 50);

		int lastY = 0;
		for (int i = 1; i < 200; i++) {
			p.moveRight(1);
//			g.drawLine(i - 1, lastY, i,
//					lastY = (int) s.getForce()[0] + 100);
		}
	}

}
