package com.storassa.java.simulation;

import java.util.ArrayList;
import java.util.List;

public class Particle {

	private double[] position;
	private double[] momentum;
	private double mass;
	private int dimensions;
	private double[] force;
	private long currentTime;

	public Particle(double _mass, double[] _position, double[] _momentum,
			long _time) {
		dimensions = 3;
		mass = _mass;
		position = _position;
		momentum = _momentum;

		force = new double[dimensions];
		currentTime = _time;
	}

	public Particle(Particle _p) {
		dimensions = _p.getPosition().length;
		position = new double[dimensions];
		momentum = new double[dimensions];
		for (int i = 0; i < dimensions; i++) {
			position[i] = _p.getPosition()[i];
			momentum[i] = _p.getMomentum()[i];
		}
		mass = _p.getMass();
		force = new double[dimensions];
		currentTime = _p.getTime();
	}

	public Particle(double _mass, double[] _position, double[] _momentum,
			int _dimensions, long _time) {
		dimensions = _dimensions;
		mass = _mass;
		position = _position;
		momentum = _momentum;

		force = new double[dimensions];
		currentTime = _time;
	}

	public String toString() {
		return "x: " + getPosition()[0] + ", y: " + getPosition()[1];
	}

	public void setForce(double[] _force) {
		force = _force;
	}

	public void setPosition(double[] _pos) {
		position = _pos;
	}

	public void moveUp(double l) {
		position[1] -= l;
	}

	public void moveDown(double l) {
		position[1] += l;
	}

	public void moveRight(double l) {
		position[0] += l;
	}

	public void moveLeft(double l) {
		position[0] -= l;
	}

	public void updateState(long time) {
		long elapsedTime = -currentTime + (currentTime = time);

		for (int i = 0; i < dimensions; i++) {
			position[i] += momentum[i] / mass * elapsedTime / 1000;
			momentum[i] += force[i] / mass * elapsedTime / 1000;
		}
	}

	public double[] getPosition() {
		return position;
	}

	public double[] getMomentum() {
		return momentum;
	}

	public double[] getForce() {
		return force;
	}

	public double getMass() {
		return mass;
	}

	public long getTime() {
		return currentTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
