package com.alexkang.x3matrixcalculator.calculations;

public class Rational {
	
	int num;
	int denom;
	
	public Rational(int num) {
		this.num = num;
		this.denom = 1;
	}
	
	public Rational(int num, int denom) {
		this.num = num;
		this.denom = denom;
		this.simplify();
	}
	
	private int gcd(int num, int denom) {
		return denom == 0 ? num: gcd(denom, num % denom);
	}
	
	private void simplify() {
		int x = gcd(num, denom);
		this.num = num / x;
		this.denom = denom / x;
		if (denom < 0) {
			this.num = num * -1;
			this.denom = denom * -1;
		}
	}
	
	public int getNum() {
		return num;
	}
	
	public int getDenom() {
		return denom;
	}
	
	public void setNum(int n) {
		this.num = n;
		this.simplify();
	}
	
	public void setDenom(int n) {
		this.denom = n;
		this.simplify();
	}
	
	public String getString() {
		if (denom == 1) {
			return num + "";
		}
		return num + "/" + denom;
	}
}
