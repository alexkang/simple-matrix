package com.alexkang.x3matrixcalculator.calculations;

public class RationalBasic {
	
	public static Rational[][] transpose(Rational[][] matrix) {
		Rational[][] result = new Rational[matrix.length][matrix[0].length];
		
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}
		
		return result;
	}
	
	public static Rational[][] add(Rational[][] matrix1, Rational[][] matrix2) {
		Rational[][] sum = new Rational[matrix1.length][matrix1[0].length];
		for (int i=0; i<sum.length; i++) {
			for (int j=0; j<sum[0].length; j++) {
			sum[i][j] = RationalCalc.addRat(matrix1[i][j], matrix2[i][j]);
			}
		}
		return sum;
	}
	
	public static Rational[][] sub(Rational[][] matrix1, Rational[][] matrix2) {
		for (int i=0; i<matrix2.length; i++) {
			for (int j=0; j<matrix2[0].length; j++) {
				matrix2[i][j] = RationalCalc.mulRat(new Rational(-1), matrix2[i][j]);
			}
		}
		return add(matrix1, matrix2);
	}
	
	public static Rational[][] mul(Rational[][] matrix1, Rational[][] matrix2) {
		Rational[][] product = new Rational[matrix1.length][matrix2[0].length];
		
		for (int i=0; i<matrix1.length; i++) {
			for (int j=0; j<matrix2[0].length; j++) {
				Rational element = new Rational(0);
				for (int k=0; k<matrix1[0].length; k++) {
					element = RationalCalc.addRat(element, 
							RationalCalc.mulRat(matrix1[i][k], matrix2[k][j])
							);
				}
				product[i][j] = element;
			}
		}
		
		return product;
	}
}
