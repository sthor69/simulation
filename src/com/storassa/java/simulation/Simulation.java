package com.storassa.java.simulation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Simulation {

	private static final int NUM_ROW = 50;
	private static final int NUM_COL = 50;
	private final static int K_SPRING = 30;
	private static final int STEP = 800 / NUM_ROW;
	
	static int numParticle;
	static Particle[] p;
	static Mass m;
	static SpringDraw sp;
	static int tempIntVar;
	static Spring[][] s;
	static boolean interrupted;

	public static void main(String[] args) throws IOException {

		JFrame f = new JFrame("move");
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 1000);
		f.setTitle("Moving Circle");
		f.setBackground(Color.GREEN);

		numParticle = NUM_COL * NUM_ROW;
		p = new Particle[numParticle];

		for (int i = 0; i < NUM_ROW; i++)
			for (int t = 0; t < NUM_COL; t++)
				p[i * NUM_COL + t] = new Particle(1, new double[] {
						100 + STEP * t, 100 + STEP * i }, new double[] { 0, 0 }, 2,
						System.currentTimeMillis());

		p[1025].moveUp(STEP / 4);

		m = new Mass(p);
		f.add(m);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true)
					if (!interrupted)
						for (int i = 0; i < p.length; i++)
							p[i].updateState(System.currentTimeMillis());
			}
		});
		thread.start();

		Thread threadKey = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						System.in.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					interrupted = !interrupted;
				}
			}
		});
		threadKey.start();

		s = new Spring[p.length][p.length];

		for (int i = 0; i < NUM_COL; i++)
			for (int t = 0; t < NUM_COL; t++) {
				if (t < NUM_COL - 1) {
					s[t + 1 + i * NUM_COL][t + i * NUM_COL] = new Spring(p[t
							+ 1 + i * NUM_COL], p[t + i * NUM_COL], K_SPRING,
							STEP);
					s[t + i * NUM_COL][t + 1 + i * NUM_COL] = new Spring(p[t + i
							* NUM_COL], p[t + 1 + i * NUM_COL], K_SPRING, STEP);
				}
				if (i < NUM_COL - 1) {
					s[t + i * NUM_COL][t + (i + 1) * NUM_COL] = new Spring(p[t
							+ i * NUM_COL], p[t + (i + 1) * NUM_COL], K_SPRING,
							STEP);
					s[t + (i + 1) * NUM_COL][t + i * NUM_COL] = new Spring(p[t
							+ (i + 1) * NUM_COL], p[t + i * NUM_COL], K_SPRING,
							STEP);
				}
			}

		sp = new SpringDraw(s, NUM_COL);
		f.add(sp);

		int delay = 100; // milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// refresh
				// m.repaint();
				sp.repaint();
			}
		};
		new Timer(delay, taskPerformer).start();

		Thread threadSpring = new Thread(new Runnable() {

			@Override
			public void run() {
				// int count = 0;
				while (true) {
					if (!interrupted) {
						// set the forces on the four vertices
						p[0].setForce(Util.vectorSum(s[0][1].getForce(),
								s[0][NUM_COL].getForce()));
						p[NUM_COL - 1].setForce(Util.vectorSum(
								s[NUM_COL - 1][NUM_COL - 2].getForce(),
								s[NUM_COL - 1][2 * NUM_COL - 1].getForce()));
						p[NUM_COL * (NUM_ROW - 1)].setForce(Util.vectorSum(
								s[NUM_COL * (NUM_ROW - 1)][NUM_COL
										* (NUM_ROW - 1) + 1].getForce(),
								s[NUM_COL * (NUM_ROW - 1)][NUM_COL
										* (NUM_ROW - 2)].getForce()));
						p[NUM_COL * NUM_ROW - 1].setForce(Util.vectorSum(
								s[NUM_COL * NUM_ROW - 1][NUM_COL * NUM_ROW - 2]
										.getForce(),
								s[NUM_COL * NUM_ROW - 1][NUM_COL
										* (NUM_ROW - 1) - 1].getForce()));

						// set the forces on the upper and lower sides
						for (int i = 1; i < NUM_COL - 1; i++) {
							p[i].setForce(Util.vectorSum(
									s[i][i + 1].getForce(),
									s[i][i - 1].getForce(),
									s[i][i + NUM_COL].getForce()));
							p[i + NUM_COL * (NUM_ROW - 1)].setForce(Util
									.vectorSum(s[i + NUM_COL * (NUM_ROW - 1)][i
											+ NUM_COL * (NUM_ROW - 1) + 1]
											.getForce(), s[i + NUM_COL
											* (NUM_ROW - 1)][i + NUM_COL
											* (NUM_ROW - 1) - 1].getForce(),
											s[i + NUM_COL * (NUM_ROW - 1)][i
													+ NUM_COL * (NUM_ROW - 2)]
													.getForce()));
						}

						// set the forces on the right and left sides
						for (int i = 1; i < NUM_COL - 1; i++) {
							p[i * NUM_COL].setForce(Util.vectorSum(
									s[i * NUM_COL][i * NUM_COL + 1].getForce(),
									s[i * NUM_COL][i * NUM_COL + NUM_COL].getForce(),
									s[i * NUM_COL][i * NUM_COL - NUM_COL].getForce()));
							p[i * NUM_COL + NUM_COL - 1].setForce(Util.vectorSum(
									s[i * NUM_COL + NUM_COL - 1][i * NUM_COL + NUM_COL - 2].getForce(),
									s[i * NUM_COL + NUM_COL - 1][i * NUM_COL - 1].getForce(),
									s[i * NUM_COL + NUM_COL - 1][i * NUM_COL + NUM_COL * 2 - 1].getForce()));
						}

						// set the forces in the inner particles
						for (int i = 1; i < NUM_COL - 1; i++)
							for (int t = 1; t < NUM_COL - 1; t++)
								p[i * NUM_COL + t].setForce(Util.vectorSum(s[i * NUM_COL
										+ t][i * NUM_COL + t + 1].getForce(), s[i
										* NUM_COL + t][i * NUM_COL + t - 1].getForce(),
										s[i * NUM_COL + t][i * NUM_COL + t + NUM_COL]
												.getForce(), s[i * NUM_COL + t][i
												* NUM_COL + t - NUM_COL].getForce()));
						/*
						 * if (count++ == 100000) { m1.repaint(); m2.repaint();
						 * m3.repaint(); count = 0; }
						 */

					}
				}
			}
		});

		threadSpring.start();

	}

}
