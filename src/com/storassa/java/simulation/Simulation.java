package com.storassa.java.simulation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Simulation {

	private static final int NUM_ROW = 20;
	private static final int NUM_COL = 20;
	private final static int K_SPRING = 10;
	private static final int STEP = 600 / NUM_ROW;

	static int numParticle;
	static Particle[] p;
	static MassDraw m;
	static SpringDraw sp;
	static int tempIntVar;
	static ArrayList<Spring> springList;
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
				p[i * NUM_COL + t] = new Particle(2, new double[] {
						100 + STEP * t, 100 + STEP * i },
						new double[] { 0, 0 }, 2, System.currentTimeMillis());

		p[200].moveLeft(STEP * 3 / 4);
		p[200].moveUp(STEP * 3 / 4);

		m = new MassDraw(p);
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

		springList = new ArrayList<Spring>();

		for (int i = 0; i < NUM_COL; i++)
			for (int t = 0; t < NUM_COL; t++) {
				if (t < NUM_COL - 1) {
					springList.add(new Spring(p[t + 1 + i * NUM_COL], p[t + i
							* NUM_COL], K_SPRING, STEP));
					if (i < NUM_COL - 1)
						springList.add(new Spring(p[t + 1 + (i + 1) * NUM_COL],
								p[t + i * NUM_COL], K_SPRING, STEP * Math.sqrt(2)));
					if (i > 0)
						springList.add(new Spring(p[t + 1 + (i - 1) * NUM_COL],
								p[t + i * NUM_COL], K_SPRING, STEP * Math.sqrt(2)));
				}
				if (i < NUM_COL - 1) {
					springList.add(new Spring(p[t + i * NUM_COL], p[t + (i + 1)
							* NUM_COL], K_SPRING, STEP));
				}
			}

		sp = new SpringDraw(springList, NUM_COL);
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
						for (Spring spring : springList)
							spring.update();
					}
				}
			}
		});

		threadSpring.start();

	}
}
