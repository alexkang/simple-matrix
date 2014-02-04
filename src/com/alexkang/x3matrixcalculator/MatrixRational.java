/*
 * The MatrixRational Parcelable class uitlizes String arrays rather
 * than Rational arrays in order to serialize the data.
 */

package com.alexkang.x3matrixcalculator;

import android.os.Parcel;
import android.os.Parcelable;

public class MatrixRational implements Parcelable {
	String[][] matrix;
	
	public MatrixRational(String[][] matrix) {
		this.matrix = matrix;
	}
	
	public MatrixRational(Parcel in) {
		matrix = (String[][]) in.readSerializable();
	}
	
	public String[][] getMatrix() {
		return matrix;
	}
	
	public void setMatrix(String[][] newMatrix) {
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

	public static final Parcelable.Creator<MatrixRational> CREATOR =
			new Parcelable.Creator<MatrixRational>() {
		
		@Override
		public MatrixRational createFromParcel(Parcel in) {
			return new MatrixRational(in);
		}
		
		@Override
		public MatrixRational[] newArray(int size) {
			return new MatrixRational[size];
		}
	};
}
