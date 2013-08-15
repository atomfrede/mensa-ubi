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
package de.atomfrede.android.mensa.ubi.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DailyMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2288937496446651731L;
	
	private List<Menu> menues;
	private String date;

	public void addMenu(Menu menu) {
		if (menues == null)
			menues = new ArrayList<Menu>();
		menues.add(menu);
	}

	public List<Menu> getMenues() {
		if (menues == null)
			menues = new ArrayList<Menu>();
		return menues;
	}

	public void setMenues(List<Menu> menues) {
		this.menues = menues;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShortendDate() {
		String[] splitted = date.split(",");
		String dateTemp = splitted[1].trim();
		int lenght = dateTemp.length();
		dateTemp = dateTemp.substring(0, lenght - 4).trim();

		String dayTemp = splitted[0].trim();
		dayTemp = dayTemp.substring(0, 2);

		return dayTemp + " " + dateTemp;
	}

}
