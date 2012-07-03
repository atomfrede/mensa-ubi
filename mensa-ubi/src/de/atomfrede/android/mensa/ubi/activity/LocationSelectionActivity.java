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
package de.atomfrede.android.mensa.ubi.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.*;
import de.atomfrede.android.mensa.ubi.data.*;
import de.atomfrede.android.mensa.ubi.util.Util;

public class LocationSelectionActivity extends SherlockListActivity {

	public static String TAG = "LocationSelectionActivity";

	private static final boolean refreshAlways = false;
	String[] locations;
	SharedPreferences settings;
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_selection);

		locations = getResources().getStringArray(R.array.locations);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, locations);
		setListAdapter(adapter);

		settings = getSharedPreferences(Constants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);

		if (refreshAlways)
			downloadData(true);
		else
			downloadData(refreshRequired());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (position == 0) {
			Intent mensaIntent = new Intent(this, MensaActivity.class);
			startActivity(mensaIntent);
		}
		if (position == 1) {
			Intent westendIntent = new Intent(this, WestendRestaurantActivity.class);
			startActivity(westendIntent);
		}
		if (position == 2) {
			Intent kurtSchumacherIntent = new Intent(this, KurtSchumacherActivity.class);
			startActivity(kurtSchumacherIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			downloadData(true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean refreshRequired() {
		if (!settings.contains(Constants.LAST_UPDATE_KEY))
			return true;

		int lastUpdate = settings.getInt(Constants.LAST_UPDATE_KEY, -1);
		if (Util.getWeekOfYear() > lastUpdate)
			return true;
		return false;

	}

	public void downloadData(boolean reload) {
		Log.d(TAG, "Downloading data, will reload: " + reload);
		LoadAndParseXhtml task = new LoadAndParseXhtml();
		task.execute(reload);
	}

	private class LoadAndParseXhtml extends AsyncTask<Boolean, Integer, MealPlan> {

		private WeeklyMenu loadMensaMeal(boolean reload) throws Exception {
			String mensaXhtml = settings.getString(Constants.MENSA_XML_KEY, null);
			Log.d(TAG, "MensaXML from settings: " + mensaXhtml);
			return Parser.parseMenu(reload, mensaXhtml, settings, Constants.mensaUrl);
		}

		private WeeklyMenu loadWestendRestaurantMeal(boolean reload) throws Exception {
			String westendXml = settings.getString(Constants.WESTEND_RESTAURANT_XML_KEY, null);
			return Parser.parseMenu(reload, westendXml, settings, Constants.westendRestaurantUrl);
		}

		private WeeklyMenu loadKurtSchumacherMeal(boolean reload) throws Exception {
			String kurtSchumacherXml = settings.getString(Constants.KURT_SCHUMACHER_XML_KEY, null);
			return Parser.parseMenu(reload, kurtSchumacherXml, settings, Constants.fhKurtSchumacherUrl);
		}

		@Override
		protected void onPreExecute() {
//			dialog = ProgressDialog.show(LocationSelectionActivity.this, getResources().getText(R.string.loading_title),
//					getResources().getText(R.string.loading_text), false);
			dialog = new ProgressDialog(LocationSelectionActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setTitle(getResources().getText(R.string.loading_title));
			dialog.setMessage(getResources().getText(R.string.loading_text));
			dialog.setCancelable(false);
			
			dialog.show();
		}

		protected void onProgressUpdate(Integer... progress) {
			dialog.setProgress(progress[0]);
		}

		@Override
		protected MealPlan doInBackground(Boolean... params) {
			try {
				MealPlan mealPlan = MealPlan.getInstance();
				boolean reload = params[0];
				mealPlan.setMensaMenu(loadMensaMeal(reload));
				publishProgress(33);
				mealPlan.setWestendRestauranMenu(loadWestendRestaurantMeal(reload));
				publishProgress(33 * 2);
				mealPlan.setKurtSchuhmacherMenu(loadKurtSchumacherMeal(reload));
				publishProgress(100);
				return mealPlan;
			} catch (Exception e) {
				Log.e(TAG, "Could not load Data from remote. ", e);
			}
			return null;
		}

		protected void onPostExecute(MealPlan result) {
			settings.edit().putInt(Constants.LAST_UPDATE_KEY, Util.getWeekOfYear()).commit();
			if (dialog.isShowing())
				dialog.dismiss();
		}
	}

}
