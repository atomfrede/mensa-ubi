/*
 *  Copyright 2012 Frederik Hahne
 *  
 *  This file is part of Mensa UBI.
 *
 *  Mensa UBI is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Mensa UBI is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Mensa UBI.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.atomfrede.android.mensa.ubi.activity.meals.weekly;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.adapter.WeekdayPagerAdapter;
import de.atomfrede.android.mensa.ubi.location.LocationSelectionActivity;
@Deprecated
public abstract class AbstractWeeklyMenuActivity extends SherlockFragmentActivity {

	protected String[] weekdays;

	protected WeekdayPagerAdapter mAdapter;
	protected ViewPager mPager;
	protected PageIndicator mIndicator;
	
	protected SharedPreferences settings;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = getSharedPreferences(Constants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);
		setContentView(R.layout.activity_weekly_meal);
		weekdays = getResources().getStringArray(R.array.weekdays_short);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	protected abstract void reloadData();
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			showAboutDialog();
			return true;
		case android.R.id.home:
			super.onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.meal, menu);
		return true;
	}
	
	protected void showAboutDialog() {
		Dialog dialog = new Dialog(this, getTheme(true));
		
		dialog.setContentView(R.layout.about_dialog);
		dialog.setTitle(getResources().getString(R.string.menu_about) + " " + getResources().getString(R.string.app_name));
		dialog.setCancelable(true);

		Button feedbackButton = (Button) dialog.findViewById(R.id.feedbackButton);
		feedbackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendFeedbackMail();
			}
		});

		String app_ver = "";
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.v("AbstractWeeklyMenuActivity", e.getMessage());
		}

		TextView versionName = (TextView) dialog.findViewById(R.id.textView1);
		versionName.setText("Version " + app_ver);
		dialog.show();
	}

	protected void sendFeedbackMail() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "atomfrede@gmail.com" });

		startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_provide_by)));
	}
	
	private int getTheme(boolean light) {
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	        return light ? android.R.style.Theme_DeviceDefault_Light_Dialog : android.R.style.Theme_DeviceDefault_Dialog;
	    }
	    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	        return light ? android.R.style.Theme_Holo_Light_Dialog : android.R.style.Theme_Holo_Dialog;
	    }
	    else{
	        return android.R.style.Theme_Dialog;
	    }
	}
	
	/**
	 * This methods selects either today, or if today is weekend selects monday
	 * on inital display of the meal overview. Keep in mind: SUNDAY = 1 MONDAY =
	 * 2 TUESDAY = 3 WEDNESDAY = 4 THURSDAY = 5 FRIDAY = 6 SATURDAY = 7
	 * 
	 * But the pager adapter start with 0!
	 */
	protected void selectInitialDay() {
		Calendar today = Calendar.getInstance();
		int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 2:
			mIndicator.setCurrentItem(0);
			break;
		case 3:
			mIndicator.setCurrentItem(1);
			break;
		case 4:
			mIndicator.setCurrentItem(2);
			break;
		case 5:
			mIndicator.setCurrentItem(3);
			break;
		case 6:
			mIndicator.setCurrentItem(4);
			break;
		default:
			mIndicator.setCurrentItem(0);
			break;
		}
	}
}
