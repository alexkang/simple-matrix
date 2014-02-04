package com.alexkang.x3matrixcalculator.calculations;

public class Basic {

	public static double[][] transpose(double[][] matrix) {
		double[][] result = new double[matrix.length][matrix[0].length];
		
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}
		
		return result;
	}
	
	public static double[][] add(double[][] matrix1, double[][] matrix2) {
		double[][] sum = new double[matrix1.length][matrix1[0].length];
		for (int i=0; i<sum.length; i++) {
			for (int j=0; j<sum[0].length; j++) {
			sum[i][j] = Math.round((matrix1[i][j] + matrix2[i][j]) * 1000.0) / 1000.0;
			}
		}
		return sum;
	}
	
	public static double[][] sub(double[][] matrix1, double[][] matrix2) {
		for (int i=0; i<matrix2.length; i++) {
			for (int j=0; j<matrix2[0].length; j++) {
				matrix2[i][j] = -1 * matrix2[i][j];
			}
		}
		return add(matrix1, matrix2);
	}
	
	public static double[][] mul(double[][] matrix1, double[][] matrix2) {
		double[][] product = new double[matrix1.length][matrix2[0].length];
		
		for (int i=0; i<matrix1.length; i++) {
			for (int j=0; j<matrix2[0].length; j++) {
				double element = 0;
				for (int k=0; k<matrix1[0].length; k++) {
					element += matrix1[i][k] * matrix2[k][j];
				}
				product[i][j] = Math.round(element * 1000.0) / 1000.0;
			}
		}
		
		return product;
	}
}
