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

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.fh.KurtSchumacherActivity;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.fh.WilhelmBertelsmannActivity;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.hs.music.MusicActivity;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.hs.owl.*;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.ubi.MensaActivity;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.ubi.WestendRestaurantActivity;
import de.atomfrede.android.mensa.ubi.data.*;
import de.atomfrede.android.mensa.ubi.util.Util;

@EFragment(R.layout.fragment_location_selection)
public class LocationSelectionFragment extends SherlockFragment {

	public static final String TAG = "LocationSelectionFragment";

	public boolean isDualPane = false;
	public boolean refreshAlways = false;
	public String[] locations;
	public SharedPreferences settings;
	public ArrayAdapter<String> mAdapter;
	public ProgressDialog dialog;

	@ViewById(R.id.location_list)
	public ListView mlocationList;

	@AfterInject
	public void afterInject() {
		locations = getResources().getStringArray(R.array.locations);
		mAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, locations);
		settings = getActivity().getSharedPreferences(Constants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);

		if (refreshAlways)
			downloadData(true);
		else
			downloadData(refreshRequired());
	}

	@AfterViews
	public void afterViews() {
		mlocationList.setAdapter(mAdapter);
	}

	@ItemClick(R.id.location_list)
	public void onListItemClicked(int position) {
		if (position == 0) {
			Intent mensaIntent = new Intent(this.getActivity(), MensaActivity.class);
			startActivity(mensaIntent);
		}
		if (position == 1) {
			Intent westendIntent = new Intent(this.getActivity(), WestendRestaurantActivity.class);
			startActivity(westendIntent);
		}
		if (position == 2) {
			Intent kurtSchumacherIntent = new Intent(this.getActivity(), KurtSchumacherActivity.class);
			startActivity(kurtSchumacherIntent);
		}
		if (position == 3) {
			Intent bertelsmannIntent = new Intent(this.getActivity(), WilhelmBertelsmannActivity.class);
			startActivity(bertelsmannIntent);
		}

		if (position == 4) {
			Intent detmoldIntent = new Intent(this.getActivity(), DetmoldActivity.class);
			startActivity(detmoldIntent);
		}
		if (position == 5) {
			Intent lemgoIntent = new Intent(this.getActivity(), LemgoActivity.class);
			startActivity(lemgoIntent);
		}
		if (position == 6) {
			Intent hoexterIntent = new Intent(this.getActivity(), HoexterActivity.class);
			startActivity(hoexterIntent);
		}
		if (position == 7) {
			Intent musicIntent = new Intent(this.getActivity(), MusicActivity.class);
			startActivity(musicIntent);
		}
	}

	public boolean refreshRequired() {
		if (!settings.contains(Constants.LAST_UPDATE_KEY))
			return true;

		int lastUpdate = settings.getInt(Constants.LAST_UPDATE_KEY, -1);
		if (Util.getWeekOfYear() > lastUpdate)
			return true;
		return false;

	}

	public void downloadData(boolean reload) {
		Log.d(TAG, "Downloading data, will reload: " + reload);
		if (MealPlan.getInstance().getMensaMenu() == null || reload) {
			LoadAndParseXhtml task = new LoadAndParseXhtml();
			task.execute(reload);
		}
	}

	private class LoadAndParseXhtml extends AsyncTask<Boolean, Integer, MealPlan> {

		private WeeklyMenu loadMensaMeal(boolean reload) throws Exception {
			String mensaXhtml = settings.getString(Constants.MENSA_XML_KEY, null);
			Log.d(TAG, "MensaXML from settings: " + mensaXhtml);
			return Parser.parseMenu(reload, mensaXhtml, settings, Constants.mensaUrl, Constants.MENSA_XML_KEY);
		}

		private WeeklyMenu loadWestendRestaurantMeal(boolean reload) throws Exception {
			String westendXml = settings.getString(Constants.WESTEND_RESTAURANT_XML_KEY, null);
			return Parser.parseMenu(reload, westendXml, settings, Constants.westendRestaurantUrl, Constants.WESTEND_RESTAURANT_XML_KEY);
		}

		private WeeklyMenu loadKurtSchumacherMeal(boolean reload) throws Exception {
			String kurtSchumacherXml = settings.getString(Constants.KURT_SCHUMACHER_XML_KEY, null);
			return Parser.parseMenu(reload, kurtSchumacherXml, settings, Constants.fhKurtSchumacherUrl, Constants.KURT_SCHUMACHER_XML_KEY);
		}

		private WeeklyMenu loadWilhelmBerterlsmannMeal(boolean reload) throws Exception {
			String bertelsmannXml = settings.getString(Constants.WILHELM_BERTELSMANN_XML_KEY, null);
			return Parser.parseMenu(reload, bertelsmannXml, settings, Constants.wilhelmBerterlsmannUrl, Constants.WILHELM_BERTELSMANN_XML_KEY);
		}

		@Deprecated
		private WeeklyMenu loadMindenMeal(boolean reload) throws Exception {
			String mindenXml = settings.getString(Constants.MINDEN_XML_KEY, null);
			return Parser.parseMenu(reload, mindenXml, settings, Constants.mindenUrl, Constants.MINDEN_XML_KEY);
		}

		private WeeklyMenu loadDetmoldMeal(boolean reload) throws Exception {
			String detmoldXml = settings.getString(Constants.DETMOLD_XML_KEY, null);
			return Parser.parseMenu(reload, detmoldXml, settings, Constants.detmoldUrl, Constants.DETMOLD_XML_KEY);
		}

		private WeeklyMenu loadLemgoMeal(boolean reload) throws Exception {
			String lemgoXml = settings.getString(Constants.LEMGO_XML_KEY, null);
			return Parser.parseMenu(reload, lemgoXml, settings, Constants.lemgoUrl, Constants.LEMGO_XML_KEY);
		}

		private WeeklyMenu loadHoexterMeal(boolean reload) throws Exception {
			String hoexterXml = settings.getString(Constants.HOEXTER_XML_KEY, null);
			return Parser.parseMenu(reload, hoexterXml, settings, Constants.hoexterUrl, Constants.HOEXTER_XML_KEY);
		}

		private WeeklyMenu loadMusicMeal(boolean reload) throws Exception {
			String musicXml = settings.getString(Constants.MUSIC_XML_KEY, null);
			return Parser.parseMenu(reload, musicXml, settings, Constants.musicUrl, Constants.MUSIC_XML_KEY);
		}

		@Override
		protected void onPreExecute() {
			// dialog = ProgressDialog.show(LocationSelectionActivity.this,
			// getResources().getText(R.string.loading_title),
			// getResources().getText(R.string.loading_text), false);
			// Dialog dialog = new Dialog(this,
			// R.style.Theme_Sherlock_Light_Dialog);
			dialog = new ProgressDialog(LocationSelectionFragment.this.getActivity());
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
				publishProgress(12);

				mealPlan.setWestendRestauranMenu(loadWestendRestaurantMeal(reload));
				publishProgress(12 * 2);

				mealPlan.setKurtSchuhmacherMenu(loadKurtSchumacherMeal(reload));
				publishProgress(12 * 3);

				mealPlan.setWilhemBertelsmannMenu(loadWilhelmBerterlsmannMeal(reload));
				publishProgress(12 * 4);

				// mealPlan.setMindenMenu(loadMindenMeal(reload));
				// publishProgress(11 * 5);

				mealPlan.setDetmoldMenu(loadDetmoldMeal(reload));
				publishProgress(12 * 6);

				mealPlan.setLemgoMenu(loadLemgoMeal(reload));
				publishProgress(12 * 7);

				mealPlan.setHoexterMenu(loadHoexterMeal(reload));
				publishProgress(12 * 8);

				mealPlan.setMusicMenu(loadMusicMeal(reload));
				publishProgress(12 * 9);
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
