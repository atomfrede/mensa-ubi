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
import de.atomfrede.android.mensa.ubi.data.MealPlan;
import de.atomfrede.android.mensa.ubi.data.WeeklyMenu;
import de.atomfrede.android.mensa.ubi.fragment.DailyMenuListFragment;

public class WeekdayPagerAdapter extends FragmentStatePagerAdapter {

	private String[] weekdays;
	private int location;

	public WeekdayPagerAdapter(FragmentManager fm, String[] weekdays, int location) {
		super(fm);
		this.weekdays = weekdays;
		this.location = location;
	}
	@Override
	public int getItemPosition(Object object) {
		  return POSITION_NONE;
	}
	
	@Override
	public Fragment getItem(int tab) {
		switch (location) {
		case Constants.LOC_MENSA:
			return getDataFragment(MealPlan.getInstance().getMensaMenu(), tab);
		case Constants.LOC_MENSA_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getMensaMenuNext(), tab);
		case Constants.LOC_WESTEND_RESTAURANT:
			return getDataFragment(MealPlan.getInstance().getWestendRestauranMenu(), tab);
		case Constants.LOC_WESTEND_RESTAURANT_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getWestendMenuNext(), tab);
		case Constants.LOC_KURT_SCHUHMACHER:
			return getDataFragment(MealPlan.getInstance().getKurtSchuhmacherMenu(), tab);
		case Constants.LOC_WILHELM_BERTELSMANN:
			return getDataFragment(MealPlan.getInstance().getWilhemBertelsmannMenu(), tab);
		case Constants.LOC_DETMOLD:
			return getDataFragment(MealPlan.getInstance().getDetmoldMenu(), tab);
		case Constants.LOC_LEMGO:
			return getDataFragment(MealPlan.getInstance().getLemgoMenu(), tab);
		case Constants.LOC_HOEXTER:
			return getDataFragment(MealPlan.getInstance().getHoexterMenu(), tab);
		case Constants.LOC_MUSIC:
			return getDataFragment(MealPlan.getInstance().getMusicMenu(), tab);
		case Constants.LOC_DETMOLD_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getDetmoldMenuNext(), tab);
		case Constants.LOC_HOEXTER_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getHoexterMenuNext(), tab);
		case Constants.LOC_LEMGO_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getLemgoMenuNext(), tab);
		case Constants.LOC_MUSIC_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getMusicMenuNext(), tab);
		case Constants.LOC_WILHELM_BERTELSMANN_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getWilhemBertelsmannMenuNext(), tab);
		case Constants.LOC_KURT_SCHUHMACHER_NEXT_WEEK:
			return getDataFragment(MealPlan.getInstance().getKurtSchuhmacherMenuNext(), tab);
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		switch (location) {
		case Constants.LOC_MENSA:
			return MealPlan.getInstance().getMensaMenu().getDailyMenues().size();
		case Constants.LOC_WESTEND_RESTAURANT:
			return MealPlan.getInstance().getWestendRestauranMenu().getDailyMenues().size();
		case Constants.LOC_KURT_SCHUHMACHER:
			return MealPlan.getInstance().getKurtSchuhmacherMenu().getDailyMenues().size();
		case Constants.LOC_WILHELM_BERTELSMANN:
			return MealPlan.getInstance().getWilhemBertelsmannMenu().getDailyMenues().size();
		case Constants.LOC_DETMOLD:
			return MealPlan.getInstance().getDetmoldMenu().getDailyMenues().size();
		case Constants.LOC_HOEXTER:
			return MealPlan.getInstance().getHoexterMenu().getDailyMenues().size();
		case Constants.LOC_LEMGO:
			return MealPlan.getInstance().getLemgoMenu().getDailyMenues().size();
		case Constants.LOC_MUSIC:
			return MealPlan.getInstance().getMusicMenu().getDailyMenues().size();
		default:
			return weekdays.length;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		//This is just a temporary work around to avoid crashes.
//		if(MealPlan.getInstance().getMensaMenu() == null){
//			return weekdays[position];
//		}
		int min = 0;
		switch (location) {
		case Constants.LOC_MENSA:
			min = Math.min(MealPlan.getInstance().getMensaMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getMensaMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_MENSA_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getMensaMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getMensaMenuNext().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_WESTEND_RESTAURANT_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getWestendMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getWestendMenuNext().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_WESTEND_RESTAURANT:
			min = Math.min(MealPlan.getInstance().getWestendRestauranMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getWestendRestauranMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_KURT_SCHUHMACHER:
			min = Math.min(MealPlan.getInstance().getKurtSchuhmacherMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getKurtSchuhmacherMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_KURT_SCHUHMACHER_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getKurtSchuhmacherMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getKurtSchuhmacherMenuNext().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_WILHELM_BERTELSMANN:
			min = Math.min(MealPlan.getInstance().getWilhemBertelsmannMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getWilhemBertelsmannMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_WILHELM_BERTELSMANN_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getWilhemBertelsmannMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getWilhemBertelsmannMenuNext().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_DETMOLD:
			min = Math.min(MealPlan.getInstance().getDetmoldMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getDetmoldMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_DETMOLD_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getDetmoldMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getDetmoldMenuNext().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_HOEXTER:
			min = Math.min(MealPlan.getInstance().getHoexterMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getHoexterMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_HOEXTER_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getHoexterMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getHoexterMenuNext().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_LEMGO:
			min = Math.min(MealPlan.getInstance().getLemgoMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getLemgoMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_LEMGO_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getLemgoMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getLemgoMenuNext().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_MUSIC:
			min = Math.min(MealPlan.getInstance().getMusicMenu().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getMusicMenu().getDailyMenues().get(min).getShortendDate();
		case Constants.LOC_MUSIC_NEXT_WEEK:
			min = Math.min(MealPlan.getInstance().getMusicMenuNext().getDailyMenues().size()-1, position);
			return MealPlan.getInstance().getMusicMenuNext().getDailyMenues().get(min).getShortendDate();
		default:
			return weekdays[position];
		}
	}

	private DailyMenuListFragment getDataFragment(WeeklyMenu weeklyMenu, int tab) {
		int min = Math.min(weeklyMenu.getDailyMenues().size()-1, tab);
		switch (tab) {
		case 0:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 1:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 2:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 3:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 4:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 5:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 6:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 7:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		case 8:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(min));
		default:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(0));
		}
	}
}
