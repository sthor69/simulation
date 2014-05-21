package com.storassa.java.simulation;

public class Force {
	private double[] components;
	private int dim;
	double modulus;
	
	public Force (double[] _components) {
		setComponents(_components);
	}
	
	public void setComponents (double[] _components) {
		components = new double[_components.length];
		for (int i = 0; i < components.length; i++)
		components[i] = _components[i];
		dim = _components.length;
		
		modulus = 0;
		for (int i = 0; i < dim; i++)
		modulus += Math.pow(components[i], 2);
		modulus = Math.sqrt(modulus);
	}
	
	public double[] getComponents() {
		return components;
	}
		
	public double getModulus() {
		return modulus;
	}
	
	public int getDim() {
		return dim;
	}
}
