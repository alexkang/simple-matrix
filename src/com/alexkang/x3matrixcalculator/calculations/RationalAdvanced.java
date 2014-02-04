package com.alexkang.x3matrixcalculator.calculations;

public class RationalAdvanced {
	
	private static Rational[][] removeElement(Rational[][] list, int x) {
		if (x == 0) {
			Rational[][] listFinal = new Rational[list.length - 1][];
			System.arraycopy(list, 1, listFinal, 0, listFinal.length);
			return listFinal;
		}
		
		Rational[][] rest = new Rational[list.length - 1][];
		System.arraycopy(list, 1, rest, 0, rest.length);
		
		Rational[][] l = removeElement(rest, x-1);
		Rational[][] listFinal = new Rational[list.length - 1][];
		listFinal[0] = list[0];
		
		for (int i=0; i<l.length; i++) {
			listFinal[i+1] = l[i];
		}
		
		return listFinal;
	}
	
	private static Rational[] removeElement2(Rational[] list, int x) {
		if (x == 0) {
			Rational[] listFinal = new Rational[list.length - 1];
			System.arraycopy(list, 1, listFinal, 0, listFinal.length);
			return listFinal;
		}
		
		Rational[] rest = new Rational[list.length - 1];
		System.arraycopy(list, 1, rest, 0, rest.length);
		
		Rational[] l = removeElement2(rest, x-1);
		Rational[] listFinal = new Rational[list.length - 1];
		listFinal[0] = list[0];
		
		for (int i=0; i<l.length; i++) {
			listFinal[i+1] = l[i];
		}
		
		return listFinal;
	}
	
	private static Rational[][] slicer(Rational[][] matrix, int m, int n) {
		Rational[][] matrixM = removeElement(matrix, m);
		Rational[][] matrixFinal = new Rational[matrixM.length][];
		
		for (int i=0; i<matrixM.length; i++) {
			matrixFinal[i] = removeElement2(matrixM[i], n);
		}
		
		return matrixFinal;
	}
	
	private static Rational cofactor(Rational[][] matrix, int m, int n) {
		Rational[][] sub = slicer(matrix, m, n);
		return RationalCalc.mulRat(
				new Rational((int) Math.pow(-1, m+n)), determinant(sub)
				);
	}
	
	private static Rational[][] adjugate(Rational[][] matrix) {
		Rational[][] result = new Rational[matrix.length][matrix[0].length];
		
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[0].length; j++) {
				result[i][j] = cofactor(matrix, i, j);
			}
		}
		
		return RationalBasic.transpose(result);
	}

	public static Rational determinant(Rational[][] matrix) {
		if (matrix.length == 2) {
			return RationalCalc.subRat(
					RationalCalc.mulRat(
							matrix[0][0], matrix[1][1]), 
					RationalCalc.mulRat(
							matrix[0][1], matrix[1][0])
					);
		}
		Rational total = new Rational(0);
		for (int i=0; i<matrix.length; i++) {
			total = RationalCalc.addRat(
					total, RationalCalc.mulRat(
							RationalCalc.mulRat(
									new Rational((int) Math.pow(-1, i)), 
									matrix[0][i]), 
							determinant(slicer(matrix, 0, i))
							)
					);
		}
		return total;
	}
	
	static public Rational[][] inverse(Rational[][] matrix) {
		try {
			Rational det = determinant(matrix);
			if (det.getNum() == 0 || det.getDenom() == 0) {
				Rational[][] errorMatrix = new Rational[matrix.length][matrix.length];
				for (int i=0; i<matrix.length; i++) {
					for (int j=0; j<matrix.length; j++) {
						errorMatrix[i][j] = new Rational(0);
					}
				}
				return errorMatrix;
			}
			
			Rational[][] inv = adjugate(matrix);
			Rational oneOverDet = new Rational(
					det.getDenom(),
					det.getNum()
					);
			
			if (matrix.length == 2) {
				inv[0][0] = RationalCalc.mulRat(oneOverDet, matrix[1][1]);
				inv[0][1] = RationalCalc.mulRat(
						oneOverDet,
						RationalCalc.mulRat(
								new Rational(-1),
								matrix[0][1])
								);
				inv[1][0] = RationalCalc.mulRat(
						oneOverDet,
						RationalCalc.mulRat(
								new Rational(-1),
								matrix[1][0])
								);
				inv[1][1] = RationalCalc.mulRat(oneOverDet, matrix[0][0]);
				return inv;
			}
			
			for (int i=0; i<matrix.length; i++) {
				for (int j=0; j<matrix[0].length; j++) {
					inv[i][j] = RationalCalc.mulRat(inv[i][j], oneOverDet);
				}
			}
	
			return inv;
		}
		
		catch (Exception e) {
			Rational[][] errorMatrix = new Rational[matrix.length][matrix.length];
			for (int i=0; i<matrix.length; i++) {
				for (int j=0; j<matrix.length; j++) {
					errorMatrix[i][j] = new Rational(0);
				}
			}
			return errorMatrix;
		}
	}
}
