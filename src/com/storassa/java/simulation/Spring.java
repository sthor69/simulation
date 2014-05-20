package com.storassa.java.simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream.GetField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Spring {

	private static final double MAX_LINEAR = 1.5;
	private static final double MAX_LENGTH = 1.5;

	private Particle p1, p2;
	private double k;
	private double restLength;
	
	private static JPanel panel;

	public Spring(Particle _p1, Particle _p2, double _k, int _restLength) {
		p1 = _p1;
		p2 = _p2;
		k = _k;
		restLength = _restLength;
	}

	public double[] getForce() {
		double distance = 0;
		double forceAbs;
		double[] force = new double[p1.getPosition().length + 1];

		for (int i = 0; i < p1.getPosition().length; i++) {
			distance += Math.pow(p1.getPosition()[i] - p2.getPosition()[i], 2);
		}
		distance = Math.sqrt(distance);

		if (distance < restLength / 10)
			forceAbs = -1 / distance + 9 / 10 * k * restLength + 10
					/ restLength;
		else if (distance > MAX_LINEAR * restLength)
			forceAbs = 1 / (-distance + MAX_LENGTH) + Math.pow(k, 2)
					* restLength + 1 / (MAX_LENGTH - k * restLength);
		else
			forceAbs = (distance - restLength) * k;

		for (int i = 0; i < force.length - 1; i++) {
			force[i] = (p2.getPosition()[i] - p1.getPosition()[i]) * forceAbs
					/ distance;
			force[force.length - 1] += Math.pow(force[i],2);
		}

		 
		return force;
	}

	public Particle[] getParticles() {
		return new Particle[] { p1, p2 };
	}

	public String toString() {

		double forceAbs = 0;
		double[] force = getForce();
		String result = "";

		result += "P1: (" + p1.getPosition()[0] + ", " + p1.getPosition()[1]
				+ "), P2: (" + p2.getPosition()[0] + ", " + p2.getPosition()[1]
				+ ")";

		result += "\nFx: " + (force[0]);
		result += "\nFy: " + (force[1]);

		for (int i = 0; i < force.length; i++)
			forceAbs += Math.pow(force[i], 2);

		result += "\nFmod: " + Math.sqrt(forceAbs);

		return result;
	}

	public static void main(String[] args) {
		
		Particle p1 = new Particle(1, new double[] { 0, 100 },
				new double[] { 0, 0 }, 2, System.currentTimeMillis());
		
		Particle p2 = new Particle(1, new double[] { 0, 100 },
				new double[] { 0, 0 }, 2, System.currentTimeMillis());
		
		Spring s = new Spring(p1, p2, 2, 50);

		System.out.println(s);
		
		JFrame frame = new JFrame();
		panel = new DrawForce(s, p1, p2);
		frame.add(panel);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setTitle("Moving Circle");
				
		panel.repaint();
	}
}
