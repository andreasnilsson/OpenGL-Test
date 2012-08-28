package com.enighma.testopengl;

import android.app.ActionBar;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.TextView.OnEditorActionListener;

public class SettingsActivity extends FragmentActivity implements ActionBar.TabListener, SeekBar.OnSeekBarChangeListener, OnEditorActionListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	SeekBar redSeekBar;
	EditText redEditText;

	private SeekBar greenSeekBar;

	private EditText greenEditText;

	private SeekBar blueSeekBar;

	private EditText blueEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));

		redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
		greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
		blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);

		redEditText = (EditText) findViewById(R.id.redEditText);
		redEditText.setOnEditorActionListener(this);
		greenEditText = (EditText) findViewById(R.id.greenEditText);
		greenEditText.setOnEditorActionListener(this);
		blueEditText = (EditText) findViewById(R.id.blueEditText);
		blueEditText.setOnEditorActionListener(this);
		


	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, show the tab contents in the
		// container
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, tab.getPosition() + 1);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		public DummySectionFragment() {
		}

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			Bundle args = getArguments();
			textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
			return textView;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (seekBar == redSeekBar) {
			syncSeekBarWithEditText(progress, redSeekBar, redEditText);
		}
		if(seekBar == greenSeekBar){
			syncSeekBarWithEditText(progress, greenSeekBar, greenEditText);
		}
		if(seekBar == blueSeekBar){
			syncSeekBarWithEditText(progress, blueSeekBar, blueEditText);
		}

	}

	public void syncSeekBarWithEditText(int progress, SeekBar seekBar, EditText editText) {
		// clamp value;
		if (progress > 255)
			progress = 255;
		if (progress < 0)
			progress = 0;
		editText.setText(Integer.toString(progress));
		seekBar.setProgress(progress);

		
		if(seekBar == redSeekBar){
			StandardModel.red = progress / 255f;	
		}
		if(seekBar == greenSeekBar){
			StandardModel.green = progress / 255f;	
		}
		if(seekBar == blueSeekBar){
			StandardModel.blue = progress / 255f;	
		}
		
		

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// System.out.println("onStartTrackingTouch");
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// System.out.println("ionStopTrackingTouch");
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			String text = v.getText().toString();
			int progress = Integer.parseInt(text.trim());
			if (v == redEditText) {
				syncSeekBarWithEditText(progress, redSeekBar, redEditText);
			}
			if(v == greenEditText){
				syncSeekBarWithEditText(progress, greenSeekBar, greenEditText);
			}
			if(v == blueEditText){
				syncSeekBarWithEditText(progress, blueSeekBar, blueEditText);
			}
		}

		return false;
	}

}
