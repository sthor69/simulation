package com.storassa.java.simulation;

public class Util {
	public static double[] vectorSum(double[]... operands) {
		double[] result = new double[operands[0].length];

		for (double[] operand : operands) {
			if (operand.length != result.length)
				throw new RuntimeException(
						"Vector sum: operands cannot have differetn sizes");
			for (int i = 0; i < operand.length; i++)
				result[i] += operand[i];
		}

		return result;
	}
}
