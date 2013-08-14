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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.fh.KurtSchumacherActivity_;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.fh.WilhelmBertelsmannActivity_;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.hs.music.MusicActivity_;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.hs.owl.*;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.ubi.MensaActivity_;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.ubi.WestendRestaurantActivity_;

@Deprecated
@EFragment(R.layout.fragment_location_selection)
public class LocationSelectionFragment extends SherlockFragment {

	public static final String TAG = "LocationSelectionFragment";

	public boolean isDualPane = false;
	public boolean refreshAlways = true;
	public String[] locations;
	public SharedPreferences settings;
	public ArrayAdapter<String> mAdapter;
	public ProgressDialog dialog;

	com.actionbarsherlock.view.Menu optionsMenu;

	@ViewById(R.id.location_list)
	public ListView mlocationList;

	@AfterInject
	public void afterInject() {
		locations = getResources().getStringArray(R.array.locations);
		mAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, locations);
		settings = getActivity().getSharedPreferences(Constants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);

//		if (refreshAlways){
//			downloadData(true);
//		}
//		else{
//			downloadData(refreshRequired());
//		}
	}

	@AfterViews
	public void afterViews() {
		mlocationList.setAdapter(mAdapter);
	}

	@ItemClick(R.id.location_list)
	public void onListItemClicked(int position) {
		if (position == 0) {
			Intent mensaIntent = new Intent(this.getActivity(), MensaActivity_.class);
			startActivity(mensaIntent);
		}
		if (position == 1) {
			Intent westendIntent = new Intent(this.getActivity(), WestendRestaurantActivity_.class);
			startActivity(westendIntent);
		}
		if (position == 2) {
			Intent kurtSchumacherIntent = new Intent(this.getActivity(), KurtSchumacherActivity_.class);
			startActivity(kurtSchumacherIntent);
		}
		if (position == 3) {
			Intent bertelsmannIntent = new Intent(this.getActivity(), WilhelmBertelsmannActivity_.class);
			startActivity(bertelsmannIntent);
		}

		if (position == 4) {
			Intent detmoldIntent = new Intent(this.getActivity(), DetmoldActivity_.class);
			startActivity(detmoldIntent);
		}
		if (position == 5) {
			Intent lemgoIntent = new Intent(this.getActivity(), LemgoActivity_.class);
			startActivity(lemgoIntent);
		}
		if (position == 6) {
			Intent hoexterIntent = new Intent(this.getActivity(), HoexterActivity_.class);
			startActivity(hoexterIntent);
		}
		if (position == 7) {
			Intent musicIntent = new Intent(this.getActivity(), MusicActivity_.class);
			startActivity(musicIntent);
		}
	}
}
