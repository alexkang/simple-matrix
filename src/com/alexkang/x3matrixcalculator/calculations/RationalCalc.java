package com.alexkang.x3matrixcalculator.calculations;

public class RationalCalc {
	
	public static Rational addRat(Rational x, Rational y) {
		int denom = x.getDenom() * y.getDenom();
		
		Rational sum = new Rational(
				x.getNum() * y.getDenom() + y.getNum() * x.getDenom(), 
				denom
				);
		
		return sum;
	}
	
	public static Rational subRat(Rational x, Rational y) {
		Rational negativeY = mulRat(new Rational(-1), y);
		return addRat(x, negativeY);
	}
	
	public static Rational mulRat(Rational x, Rational y) {
		return new Rational(
				x.getNum() * y.getNum(),
				x.getDenom() * y.getDenom());
	}
	
	public static Rational divRat(Rational x, Rational y) {
		Rational inverseY = new Rational(y.getDenom(), y.getNum());
		return mulRat(x, inverseY);
	}
}
