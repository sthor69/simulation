package com.storassa.java.simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class Mass extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Particle[] p;

	public Mass(Particle[] _p) {
		p = _p;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

/*		for (int i = 0; i < p.length; i++) {
			System.out.print("Particle " + i + ": position = "
					+ p[i].getPosition()[0] + ", momentum  = "
					+ p[i].getMomentum()[0] + ", force = "
					+ p[i].getForce()[0]);
			System.out.println();
		}
		System.out.println();*/

		for (int i = 0; i < p.length; i++) {
			double diameter = p[i].getMass() * 10;
			Ellipse2D circle = new Ellipse2D.Double(p[i].getPosition()[0], p[i].getPosition()[1],
					diameter, diameter);
			g2.fill(circle);
		}
	}
}
