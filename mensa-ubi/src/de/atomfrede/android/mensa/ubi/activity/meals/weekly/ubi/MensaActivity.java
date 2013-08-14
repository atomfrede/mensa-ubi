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
package de.atomfrede.android.mensa.ubi.activity.meals.weekly.ubi;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.AbstractWeeklyMenuActivity;
import de.atomfrede.android.mensa.ubi.data.MealPlan;
import de.atomfrede.android.mensa.ubi.data.Parser;
import de.atomfrede.android.mensa.ubi.meal.WeeklyMealFragment;

@Deprecated
@EActivity
public class MensaActivity extends AbstractWeeklyMenuActivity {

	
	SpinnerAdapter mSpinnerAdapter;
	
	@InstanceState
	int currentPosition;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("");
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.mensa_dropdown,
		          android.R.layout.simple_spinner_dropdown_item);
		
		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				exchangeFragment(itemPosition, false);
				return true;
			}
		});
		getSupportActionBar().setSelectedNavigationItem(currentPosition);		
	}
	
	private void exchangeFragment(int position, boolean initial){
		if(currentPosition != position || initial){
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction fmt = fm.beginTransaction();
			switch (position) {
			case 0:
				//Normal, current week
				fmt.replace(R.id.fragment_container, WeeklyMealFragment.newInstance(Constants.MENSA_XML_KEY, Constants.mensaUrl, Constants.LOC_MENSA));
				fmt.commit();
				break;
			case 1:
				//Next week, week+1
				fmt.replace(R.id.fragment_container, WeeklyMealFragment.newInstance(Constants.MENSA_NEXT_XML_KEY, Constants.mensaUrlNextWeek, Constants.LOC_MENSA_NEXT_WEEK));
				fmt.commit();
				break;
			default:
				break;
			}
		}
		currentPosition = position;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		exchangeFragment(currentPosition, true);
	}
	
	@Override
	protected void reloadData(){
		try{
			MealPlan.getInstance().setMensaMenu(Parser.parseMenu(true, settings.getString(Constants.MENSA_XML_KEY, null), settings, Constants.mensaUrl, Constants.MENSA_XML_KEY));
		}catch(Exception e){
			
		}
	}

}
