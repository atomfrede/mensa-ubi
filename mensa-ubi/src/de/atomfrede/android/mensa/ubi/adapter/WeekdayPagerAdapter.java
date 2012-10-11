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
package de.atomfrede.android.mensa.ubi.adapter;

import android.support.v4.app.*;
import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.activity.LocationSelectionActivity;
import de.atomfrede.android.mensa.ubi.data.MealPlan;
import de.atomfrede.android.mensa.ubi.data.WeeklyMenu;
import de.atomfrede.android.mensa.ubi.fragment.DailyMenuListFragment;

public class WeekdayPagerAdapter extends FragmentPagerAdapter {

	private String[] weekdays;
	private int location;

	public WeekdayPagerAdapter(FragmentManager fm, String[] weekdays, int location) {
		super(fm);
		this.weekdays = weekdays;
		this.location = location;
	}

	@Override
	public Fragment getItem(int tab) {
		switch (location) {
		case Constants.LOC_MENSA:
			return getDataFragment(MealPlan.getInstance().getMensaMenu(), tab);
		case Constants.LOC_WESTEND_RESTAURANT:
			return getDataFragment(MealPlan.getInstance().getWestendRestauranMenu(), tab);
		case Constants.LOC_KURT_SCHUHMACHER:
			return getDataFragment(MealPlan.getInstance().getKurtSchuhmacherMenu(), tab);
		case Constants.LOC_WILHELM_BERTELSMANN:
			return getDataFragment(MealPlan.getInstance().getWilhemBertelsmannMenu(), tab);
		case Constants.LOC_MINDEN:
			return getDataFragment(MealPlan.getInstance().getMindenMenu(), tab);
		case Constants.LOC_DETMOLD:
			return getDataFragment(MealPlan.getInstance().getDetmoldMenu(), tab);
		case Constants.LOC_LEMGO:
			return getDataFragment(MealPlan.getInstance().getLemgoMenu(), tab);
		case Constants.LOC_HOEXTER:
			return getDataFragment(MealPlan.getInstance().getHoexterMenu(), tab);
		case Constants.LOC_MUSIC:
			return getDataFragment(MealPlan.getInstance().getMusicMenu(), tab);
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return weekdays.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		//This is just a temporary work around to avoid crashes.
		if(MealPlan.getInstance().getMensaMenu() == null){
			return weekdays[position];
		}
		switch (location) {
		case Constants.LOC_MENSA:
			return MealPlan.getInstance().getMensaMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_WESTEND_RESTAURANT:
			return MealPlan.getInstance().getWestendRestauranMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_KURT_SCHUHMACHER:
			return MealPlan.getInstance().getKurtSchuhmacherMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_WILHELM_BERTELSMANN:
			return MealPlan.getInstance().getWilhemBertelsmannMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_DETMOLD:
			return MealPlan.getInstance().getDetmoldMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_HOEXTER:
			return MealPlan.getInstance().getHoexterMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_LEMGO:
			return MealPlan.getInstance().getLemgoMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_MINDEN:
			return MealPlan.getInstance().getMindenMenu().getDailyMenues().get(position).getShortendDate();
		case Constants.LOC_MUSIC:
			return MealPlan.getInstance().getMusicMenu().getDailyMenues().get(position).getShortendDate();
		default:
			return weekdays[position];
		}
	}

	private DailyMenuListFragment getDataFragment(WeeklyMenu weeklyMenu, int tab) {
		switch (tab) {
		case 0:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 1:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 2:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 3:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 4:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 5:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 6:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 7:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		case 8:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(tab));
		default:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(0));
		}
	}
}
