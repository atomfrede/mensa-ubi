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

import java.util.ArrayList;
import java.util.List;

public class WeeklyMenu {

	private String weekOfYear;
	private List<DailyMenu> dailyMenues;

	public void addDailyMenu(DailyMenu menu){
		if(dailyMenues == null)
			dailyMenues = new ArrayList<DailyMenu>();
		dailyMenues.add(menu);
	}
	
	public String getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(String weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public List<DailyMenu> getDailyMenues() {
		if(dailyMenues == null)
			dailyMenues = new ArrayList<DailyMenu>();
		return dailyMenues;
	}

	public void setDailyMenues(List<DailyMenu> menues) {
		this.dailyMenues = menues;
	}

}
