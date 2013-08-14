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
package de.atomfrede.android.mensa.ubi.location;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.ubi.R;

@Deprecated
@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_location_selection)
public class LocationSelectionActivity extends SherlockFragmentActivity {

	public static String TAG = "LocationSelectionActivity";
	
	@FragmentById(R.id.locationSelectionFragment)
	public LocationSelectionFragment mLocationSelectionFragment;
	
	@OptionsItem(R.id.menu_about)
	public void showAboutDialog() {
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
			Log.v(TAG, e.getMessage());
		}

		TextView versionName = (TextView) dialog.findViewById(R.id.textView1);
		versionName.setText("Version " + app_ver);
		dialog.show();
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
	
	public void sendFeedbackMail() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "atomfrede@gmail.com" });

		startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_provide_by)));
	}

}
