package com.storassa.java.simulation;

public class Spring {

	private Particle p1, p2;
	private double k;
	private double restLength;

	public Spring(Particle _p1, Particle _p2, double _k, int _restLength) {
		p1 = _p1;
		p2 = _p2;
		k = _k;
		restLength = _restLength;
	}

	public double[] getForce() {
		double distance = 0;
		double forceAbs;
		double[] force = new double[p1.getPosition().length];

		for (int i = 0; i < p1.getPosition().length; i++) {
			distance += Math.pow(p1.getPosition()[i] - p2.getPosition()[i], 2);
		}
		distance = Math.sqrt(distance);

		if (distance < restLength / 10)
			forceAbs = -1 / distance + 9 / 10 * k * restLength + 10
					/ restLength;
		else
			forceAbs = (distance - restLength) * k;

		for (int i = 0; i < force.length; i++) {
			force[i] = (p2.getPosition()[i] - p1.getPosition()[i]) * forceAbs
					/ distance;
		}
		
		return force;
	}

	public String toString() {
		
		double forceAbs = 0;
		double[] force = getForce();
		
		String result = "x: " + (force[0]);
		result += "\ny: " + (force[1]);
		
		for (int i = 0; i < force.length; i++)
			forceAbs += Math.pow(force[i], 2); 
		
		result += "\nmod: " + Math.sqrt(forceAbs);

		return result;
	}

	public static void main(String[] args) {
		Spring s = new Spring(new Particle(1, new double[] { 20, 0 },
				new double[] { 0, 0 }, 2, System.currentTimeMillis()),
				new Particle(1, new double[] { 10, 10 }, new double[] { 0, 0 },
						2, System.currentTimeMillis()), 2, 4);

		System.out.println(s);
	}
}
