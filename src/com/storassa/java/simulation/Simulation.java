package com.storassa.java.simulation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Simulation {

	static int numParticle;
	static Particle[] p;
	static Mass m;
	static int tempIntVar;
	static Spring[][] s;

	public static void main(String[] args) throws IOException {

		JFrame f = new JFrame("move");
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 1000);
		f.setTitle("Moving Circle");
		f.setBackground(Color.GREEN);

		numParticle = 100;
		p = new Particle[numParticle];

		for (int i = 0; i < 10; i++)
			for (int t = 0; t < 10; t++)
				p[i * 10 + t] = new Particle(1, new double[] { 100 + 50 * t,
						100 + 50 * i }, new double[] { 0, 0 }, 2,
						System.currentTimeMillis());

		p[50].setPosition(new double[] { 110, 360 });

		m = new Mass(p);
		f.add(m);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true)
					for (int i = 0; i < p.length; i++)
						p[i].updateState(System.currentTimeMillis());
			}
		});
		thread.start();

		s = new Spring[p.length][p.length];

		for (int i = 0; i < 10; i++)
			for (int t = 0; t < 10; t++) {
				if (t < 9) {
					s[t + 1 + i * 10][t + i * 10] = new Spring(
							p[t + 1 + i * 10], p[t + i * 10], K_SPRING, 50);
					s[t + i * 10][t + 1 + i * 10] = new Spring(p[t + i * 10],
							p[t + 1 + i * 10], K_SPRING, 50);
				}
				if (i < 9) {
					s[t + i * 10][t + (i + 1) * 10] = new Spring(p[t + i * 10],
							p[t + (i + 1) * 10], K_SPRING, 50);
					s[t + (i + 1) * 10][t + i * 10] = new Spring(p[t + (i + 1)
							* 10], p[t + i * 10], K_SPRING, 50);
				}
			}

		int delay = 33; // milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// refresh
				m.repaint();
			}
		};
		new Timer(delay, taskPerformer).start();

		Thread threadSpring = new Thread(new Runnable() {

			@Override
			public void run() {
				// int count = 0;
				while (true) {

					// set the forces on the four vertices
					p[0].setForce(Util.vectorSum(s[0][1].getForce(),
							s[0][10].getForce()));
					p[9].setForce(Util.vectorSum(s[9][8].getForce(),
							s[9][19].getForce()));
					p[90].setForce(Util.vectorSum(s[90][91].getForce(),
							s[90][80].getForce()));
					p[99].setForce(Util.vectorSum(s[99][98].getForce(),
							s[99][89].getForce()));

					// set the forces on the upper and lower sides
					for (int i = 1; i < 9; i++) {
						p[i].setForce(Util.vectorSum(s[i][i + 1].getForce(),
								s[i][i - 1].getForce(), s[i][i + 10].getForce()));
						p[i + 90].setForce(Util.vectorSum(
								s[i + 90][i + 91].getForce(),
								s[i + 90][i + 89].getForce(),
								s[i + 90][i + 80].getForce()));
					}

					// set the forces on the right and left sides
					for (int i = 1; i < 9; i++) {
						p[i * 10].setForce(Util.vectorSum(
								s[i * 10][i * 10 + 1].getForce(),
								s[i * 10][i * 10 + 10].getForce(),
								s[i * 10][i * 10 - 10].getForce()));
						p[i * 10 + 9].setForce(Util.vectorSum(
								s[i * 10 + 9][i * 10 + 8].getForce(),
								s[i * 10 + 9][i * 10 - 1].getForce(),
								s[i * 10 + 9][i * 10 + 19].getForce()));
					}

					// set the forces in the inner particles
					for (int i = 1; i < 9; i++)
						for (int t = 1; t < 9; t++)
							p[i * 10 + t].setForce(Util.vectorSum(
									s[i * 10 + t][i * 10 + t + 1].getForce(),
									s[i * 10 + t][i * 10 + t - 1].getForce(),
									s[i * 10 + t][i * 10 + t + 10].getForce(),
									s[i * 10 + t][i * 10 + t - 10].getForce()));
					/*
					 * if (count++ == 100000) { m1.repaint(); m2.repaint();
					 * m3.repaint(); count = 0; }
					 */
				}
			}
		});

		threadSpring.start();

	}
	
	private final static int K_SPRING = 8;
}
