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
		System.out.println("Getting the Page Title");
		String title = "";
		switch (location) {
		case Constants.LOC_MENSA:
			title = MealPlan.getInstance().getMensaMenu().getDailyMenues().get(position).getShortendDate();
			System.out.println("TITLE " + title);
			return title;
		default:
			title = weekdays[position];
			System.out.println("TITLE " + title);
			return title;
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
		default:
			return DailyMenuListFragment.newInstance(weeklyMenu.getDailyMenues().get(0));
		}
	}
}
