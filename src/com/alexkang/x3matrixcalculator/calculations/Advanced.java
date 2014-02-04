package com.alexkang.x3matrixcalculator.calculations;

public class Advanced {
	
	private static double[][] removeElement(double[][] list, int x) {
		if (x == 0) {
			double[][] listFinal = new double[list.length - 1][];
			System.arraycopy(list, 1, listFinal, 0, listFinal.length);
			return listFinal;
		}
		
		double[][] rest = new double[list.length - 1][];
		System.arraycopy(list, 1, rest, 0, rest.length);
		
		double[][] l = removeElement(rest, x-1);
		double[][] listFinal = new double[list.length - 1][];
		listFinal[0] = list[0];
		
		for (int i=0; i<l.length; i++) {
			listFinal[i+1] = l[i];
		}
		
		return listFinal;
	}
	
	private static double[] removeElement2(double[] list, int x) {
		if (x == 0) {
			double[] listFinal = new double[list.length - 1];
			System.arraycopy(list, 1, listFinal, 0, listFinal.length);
			return listFinal;
		}
		
		double[] rest = new double[list.length - 1];
		System.arraycopy(list, 1, rest, 0, rest.length);
		
		double[] l = removeElement2(rest, x-1);
		double[] listFinal = new double[list.length - 1];
		listFinal[0] = list[0];
		
		for (int i=0; i<l.length; i++) {
			listFinal[i+1] = l[i];
		}
		
		return listFinal;
	}
	
	private static double[][] slicer(double[][] matrix, int m, int n) {
		double[][] matrixM = removeElement(matrix, m);
		double[][] matrixFinal = new double[matrixM.length][];
		
		for (int i=0; i<matrixM.length; i++) {
			matrixFinal[i] = removeElement2(matrixM[i], n);
		}
		
		return matrixFinal;
	}
	
	private static double cofactor(double[][] matrix, int m, int n) {
		double[][] sub = slicer(matrix, m, n);
		return Math.pow(-1, m+n) * determinant(sub);
	}
	
	private static double[][] adjugate(double[][] matrix) {
		double[][] result = new double[matrix.length][matrix[0].length];
		
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix.length; j++) {
				result[i][j] = cofactor(matrix, i, j);
			}
		}
		
		return Basic.transpose(result);
	}

	public static double determinant(double[][] matrix) {
		if (matrix.length == 2) {
			return (matrix[0][0]*matrix[1][1]) - 
					(matrix[0][1]*matrix[1][0]);
		}
		double total = 0.0;
		for (int i=0; i<matrix.length; i++) {
			total += Math.pow(-1, i) * matrix[0][i] * determinant(slicer(matrix, 0, i));
		}
		return total;
	}
	
	static public double[][] inverse(double[][] matrix) {
		try {
			double[][] inv = adjugate(matrix);
			double oneOverDet = 1 / determinant(matrix);
			
			if (matrix.length == 2) {
				inv[0][0] = oneOverDet * matrix[1][1];
				inv[0][1] = -oneOverDet * matrix[0][1];
				inv[1][0] = -oneOverDet * matrix[1][0];
				inv[1][1] = oneOverDet * matrix[0][0];
				
				for (int i=0; i<matrix.length; i++) {
					for (int j=0; j<matrix.length; j++) {
						inv[i][j] = Math.round(inv[i][j] * 1000.0) / 1000.0;
					}
				}
				
				return inv;
			}
			
			for (int i=0; i<matrix.length; i++) {
				for (int j=0; j<matrix[0].length; j++) {
					inv[i][j] = inv[i][j] * oneOverDet;
				}
			}
			
			for (int i=0; i<matrix.length; i++) {
				for (int j=0; j<matrix.length; j++) {
					inv[i][j] = Math.round(inv[i][j] * 1000.0) / 1000.0;
				}
			}
			
			return inv;
		}
		
		catch (Exception e) {
			double[][] errorMatrix = new double[matrix.length][matrix.length];
			for (int i=0; i<matrix.length; i++) {
				for (int j=0; j<matrix.length; j++) {
					errorMatrix[i][j] = 0;
				}
			}
			return errorMatrix;
		}
	}
}
