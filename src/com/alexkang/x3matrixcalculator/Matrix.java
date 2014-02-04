package com.alexkang.x3matrixcalculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Matrix implements Parcelable {
	double[][] matrix;
	
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public Matrix(Parcel in) {
		matrix = (double[][]) in.readSerializable();
	}
	
	public double[][] getMatrix() {
		return matrix;
	}
	
	public void setMatrix(double[][] newMatrix) {
		this.matrix = newMatrix;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeSerializable(matrix);
	}

	public static final Parcelable.Creator<Matrix> CREATOR =
			new Parcelable.Creator<Matrix>() {
		
		@Override
		public Matrix createFromParcel(Parcel in) {
			return new Matrix(in);
		}
		
		@Override
		public Matrix[] newArray(int size) {
			return new Matrix[size];
		}
	};
}
