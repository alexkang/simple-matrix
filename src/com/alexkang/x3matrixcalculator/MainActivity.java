package com.alexkang.x3matrixcalculator;

import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alexkang.x3matrixcalculator.calculations.Rational;

public class MainActivity extends FragmentActivity {
	
	public final static String RESULT = "com.x3matrixcalculator.RESULT";
	
	public static int row = 3;
	public static int column = 3;
	
	public static boolean rational(Context context) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		boolean pref = sharedPref.getBoolean("pref_rational", true);
		return pref;
	}
	
	private static boolean isInvalid(String element, Context context) {
		String[] invalid = new String[] {"", ".", "+", "-", "+.", "-."};
		Toast toast = Toast.makeText(
				context, 
				"Please fill all fields properly", 
				Toast.LENGTH_SHORT);
		
		for (int i=0; i<invalid.length; i++) {
			if (element.equals(invalid[i])) {
				toast.show();
				return true;
			}
			
			else if (rational(context)) {
				try {
					Rational x = parseRational(element);
					int zeroTest = x.getNum() / x.getDenom();
					System.out.println(zeroTest); // Dead code. Used to remove annoying warning in Eclipse.
				}
				catch (Exception e) {
					toast.show();
					return true;
				}
			}
		}
		return false;
	}

	private static Rational parseRational(String n) {
		String[] nSplit = n.split("\\.");
		if (nSplit.length ==  2) {
			
			return new Rational(
					Integer.parseInt(nSplit[0]), 
					Integer.parseInt(nSplit[1])
					);
		}
		return new Rational(Integer.parseInt(n));
	}
	
	private static void clear(ViewGroup group) {
		/*
		 * This function resets all user interface elements for a
		 * specific fragment given as a ViewGroup.
		 */
		
		for (int i=0; i < group.getChildCount(); i++) {
			View view = group.getChildAt(i);
			if (view instanceof EditText) {
				((EditText) view).setText("");
			}
			else if (view.getId() == R.id.det_result) {
				((TextView) view).setText("");
			}
			else if (view instanceof ViewGroup && group.getChildCount() > 0) {
				clear((ViewGroup)view);
			}
		}
	}

	public void calculateDet(View view) {
		ViewGroup editTexts = (ViewGroup) findViewById(R.id.edit_texts_det);
		
		if (rational(this)) {
			Rational[][] matrix = new Rational[row][column];
			Rational result;
			
			for (int i=0; i<row; i++) {
				ViewGroup row = (ViewGroup) editTexts.getChildAt(i);
				for (int j=0; j<column; j++) {
					EditText element = (EditText) row.getChildAt(j);
					String elementString = element.getText().toString();
					
					if (isInvalid(elementString, this)) {
						return;
					}
					
					matrix[i][j] = parseRational(element.getText().toString());
				}
			}
			
			result = com.alexkang.x3matrixcalculator.calculations.RationalAdvanced.determinant(matrix);
			
			TextView resultField = (TextView) findViewById(R.id.det_result);
			resultField.setText(result.getString());
		}
		else {
			double[][] matrix = new double[row][column];
			double result;
			
			for (int i=0; i<row; i++) {
				ViewGroup row = (ViewGroup) editTexts.getChildAt(i);
				for (int j=0; j<column; j++) {
					EditText element = (EditText) row.getChildAt(j);
					String elementString = element.getText().toString();
					
					if (isInvalid(elementString, this)) {
						return;
					}
					
					matrix[i][j] = Double.parseDouble(element.getText().toString());
					}
				}
			
			result = com.alexkang.x3matrixcalculator.calculations.Advanced.determinant(matrix);
			
			TextView resultField = (TextView) findViewById(R.id.det_result);
			resultField.setText(result + "");
		}
	}
	
	public void calculateInv(View view) {
		Intent intent = new Intent(this, DisplayResultActivity.class);
		ViewGroup editTexts = (ViewGroup) findViewById(R.id.edit_texts_inv);
		
		if (rational(this)) {
			Rational[][] matrix = new Rational[row][column];
			Rational[][] result;
			
			for (int i=0; i<row; i++) {
				ViewGroup row = (ViewGroup) editTexts.getChildAt(i);
				for (int j=0; j<column; j++) {
					EditText element = (EditText) row.getChildAt(j);
					String elementString = element.getText().toString();
					
					if (isInvalid(elementString, this)) {
						return;
					}
					
					matrix[i][j] = parseRational(elementString);
				}
			}
		
			result = com.alexkang.x3matrixcalculator.calculations.RationalAdvanced.inverse(matrix);
			String[][] resultString = new String[row][column];
			
			for (int i=0; i<row; i++) {
				for (int j=0; j<column; j++) {
					resultString[i][j] = result[i][j].getString();
				}
			}
			
			intent.putExtra(RESULT, new MatrixRational(resultString));
			startActivity(intent);
		}
		
		else {
			double[][] matrix = new double[row][column];
			double[][] result;
			
			for (int i=0; i<row; i++) {
				ViewGroup row = (ViewGroup) editTexts.getChildAt(i);
				for (int j=0; j<column; j++) {
					EditText element = (EditText) row.getChildAt(j);
					String elementString = element.getText().toString();
					
					if (isInvalid(elementString, this)) {
						return;
					}
					
					matrix[i][j] = Double.parseDouble(elementString);
				}
			}
		
			result = com.alexkang.x3matrixcalculator.calculations.Advanced.inverse(matrix);
		
			intent.putExtra(RESULT, new Matrix(result));
			startActivity(intent);
		}
	}

	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("");
		setContentView(R.layout.activity_main);		

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		// Create action bar spinner
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(
				this, 
				R.array.dimensions,
				android.R.layout.simple_spinner_dropdown_item
				);
		
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ActionBar.OnNavigationListener mOnNavigationListener = new OnNavigationListener() {
			boolean initSelect = false;
			
			@Override
			public boolean onNavigationItemSelected(int pos, long itemId) {
				if (initSelect) {
					if (pos == 0) {
						row = 2;
						column = 2;
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						finish();
						startActivity(intent);
					}
					else if (pos == 1) {
						row = 3;
						column = 3;
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						finish();
						startActivity(intent);
					}
					else if (pos == 2) {
						row = 4;
						column = 4;
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						finish();
						startActivity(intent);
					}
				}
				else {
					initSelect = true;
				}
				return true;
			}
		};
		
		getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
		getActionBar().setSelectedNavigationItem(row - 2);
		// end creating action bar spinner
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment;
			if (position == 0) {
				fragment = new OperationFragment();
			}
			else {
				fragment = new AdvancedFragment();
			}
			Bundle args = new Bundle();
			args.putInt(OperationFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	
	public static class OperationFragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
				case R.id.action_clear:
					clear((ViewGroup) getActivity().findViewById(R.id.edit_texts));
					return true;
				case R.id.action_settings:
					Intent intent = new Intent(getActivity(), SettingsActivity.class);
					startActivity(intent);
					return true;
				default:
					return true;
			}
		}
			
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			
			View rootView;
			if (row == 2) {
				rootView = inflater.inflate(R.layout.fragment_basic_2, container, false);
			}
			else if (row == 3) {
				rootView = inflater.inflate(R.layout.fragment_basic_3, container, false);
			}
			else {
				rootView = inflater.inflate(R.layout.fragment_basic_4, container, false);
			}
			
			// Creating a Spinner that changes a button's onClick.
			Spinner spinner = (Spinner) rootView.findViewById(R.id.operation);
			ArrayAdapter<CharSequence> adapter =
					ArrayAdapter.createFromResource(
							getActivity(), 
							R.array.operations, 
							android.R.layout.simple_spinner_item
							);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			
			// Changing the onClick action
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {	
				
				public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id) {
					Button button = (Button) getActivity().findViewById(R.id.calculate);
					button.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(v.getContext(), DisplayResultActivity.class);
							ViewGroup editTexts = (ViewGroup) getActivity().findViewById(R.id.edit_texts);
							
							double[][] matrix1 = new double[row][column];
							double[][] matrix2 = new double[row][column];
							double[][] result = new double[row][column];
							
							Rational[][] matrix1Rational = new Rational[row][column];
							Rational[][] matrix2Rational = new Rational[row][column];
							Rational[][] resultRational = new Rational[row][column];
							
							for (int i=0; i<row; i++) {
								ViewGroup rowA = (ViewGroup) editTexts.getChildAt(i);
								ViewGroup rowB = (ViewGroup) editTexts.getChildAt(i+row+1);
								for (int j=0; j<column; j++) {
									EditText elementA = (EditText) rowA.getChildAt(j);
									EditText elementB = (EditText) rowB.getChildAt(j);
									String elementAString = elementA.getText().toString();
									String elementBString = elementB.getText().toString();
									
									if (isInvalid(elementAString, v.getContext())
											|| isInvalid(elementBString, v.getContext())) {
										return;
									}
									
									if (rational(v.getContext())) {
										matrix1Rational[i][j] = parseRational(elementAString);
										matrix2Rational[i][j] = parseRational(elementBString);
									}
									else {
										matrix1[i][j] = Double.parseDouble(elementA.getText().toString());
										matrix2[i][j] = Double.parseDouble(elementB.getText().toString());
									}
								}
							}
							
							// Change Calculate button depending on spinner position.
							if (pos == 0) {
								if (rational(v.getContext())) {
									resultRational = 
											com.alexkang.x3matrixcalculator.calculations.RationalBasic.add(
													matrix1Rational, 
													matrix2Rational
													);
								}
								else {
									result = 
											com.alexkang.x3matrixcalculator.calculations.Basic.add(
													matrix1, 
													matrix2
													);
								}
							}
							
							else if (pos == 1) {
								if (rational(v.getContext())) {
									resultRational = 
											com.alexkang.x3matrixcalculator.calculations.RationalBasic.sub(
													matrix1Rational, 
													matrix2Rational
													);
								}
								else {
									result = 
											com.alexkang.x3matrixcalculator.calculations.Basic.sub(
													matrix1, 
													matrix2
													);
								}
							}
							else if (pos == 2) {
								if (rational(v.getContext())) {
									resultRational = 
											com.alexkang.x3matrixcalculator.calculations.RationalBasic.mul(
													matrix1Rational, 
													matrix2Rational
													);
								}
								else {
									result = 
											com.alexkang.x3matrixcalculator.calculations.Basic.mul(
													matrix1, 
													matrix2
													);
								}
							}
							// End of button change conditions.
							
							if (rational(v.getContext())) {
								String[][] resultString = new String[row][column];
								for (int i=0; i<row; i++) {
									for (int j=0; j<column; j++) {
										resultString[i][j] = resultRational[i][j].getString();
									}
								}
								intent.putExtra(RESULT, new MatrixRational(resultString));
							}
							else {
								intent.putExtra(RESULT, new Matrix(result));
							}
							
							v.getContext().startActivity(intent);
						}
						
					});
				}
				public void onNothingSelected(AdapterView<?> parent) {}
			});
			
			return rootView;
		}
	}
	
	public static class AdvancedFragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.action_clear:
				if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
					clear((ViewGroup) getActivity().findViewById(R.id.edit_texts_det));
				}
				else {
					clear((ViewGroup) getActivity().findViewById(R.id.edit_texts_inv));
				}
				return true;
			case R.id.action_settings:
				Intent intent = new Intent(getActivity(), SettingsActivity.class);
				startActivity(intent);
				return true;
			default:
				return true;
			}
		}
	
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			
			View rootView;
			if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
				if (row == 2) {
					rootView = inflater.inflate(R.layout.fragment_determinant_2, container, false);
				}
				else if (row == 3) {
					rootView = inflater.inflate(R.layout.fragment_determinant_3, container, false);
				}
				else {
					rootView = inflater.inflate(R.layout.fragment_determinant_4, container, false);
				}
			}
			else {
				if (row == 2) {
					rootView = inflater.inflate(R.layout.fragment_inverse_2, container, false);
				}
				else if (row == 3) {
					rootView = inflater.inflate(R.layout.fragment_inverse_3, container, false);
				}
				else {
					rootView = inflater.inflate(R.layout.fragment_inverse_4, container, false);
				}
			}
			
			return rootView;
		}
	}
}
