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
package de.atomfrede.android.mensa.ubi.fragment;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import de.atomfrede.android.mensa.ubi.adapter.MenuListAdapter;
import de.atomfrede.android.mensa.ubi.data.DailyMenu;


public class DailyMenuListFragment extends SherlockListFragment {

	public static DailyMenuListFragment newInstance(DailyMenu menu) {
		DailyMenuListFragment newFragment = new DailyMenuListFragment();
		newFragment.menu = menu;
		return newFragment;
	}

	DailyMenu menu;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null && menu == null) {
			menu = (DailyMenu) savedInstanceState.getSerializable("menu");
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		MenuListAdapter menuListAdapter = new MenuListAdapter(this.getActivity(), menu.getMenues());
		setListAdapter(menuListAdapter);
//		setRetainInstance(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("menu", menu);
	}
}
