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
package de.atomfrede.android.mensa.ubi.activity.meals.weekly.hs.owl;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.AbstractWeeklyMenuActivity;
import de.atomfrede.android.mensa.ubi.adapter.WeekdayPagerAdapter;
import de.atomfrede.android.mensa.ubi.data.MealPlan;
import de.atomfrede.android.mensa.ubi.data.Parser;

public class HoexterActivity extends AbstractWeeklyMenuActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(getResources().getString(R.string.hoexter_title));

		mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new WeekdayPagerAdapter(getSupportFragmentManager(), weekdays, Constants.LOC_HOEXTER);
		mPager.setAdapter(mAdapter);

		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		mIndicator = indicator;

		selectInitialDay();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (MealPlan.getInstance().getHoexterMenu() == null) {
			// The application was resumed and before remove from memory so we
			// need to restore the menu plans
			reloadData();
		}
	}

	@Override
	protected void reloadData() {
		try {
			MealPlan.getInstance().setHoexterMenu(
					Parser.parseMenu(false, settings.getString(Constants.HOEXTER_XML_KEY, null), settings, Constants.hoexterUrl,
							Constants.HOEXTER_XML_KEY));
		} catch (Exception e) {

		}
	}
}
