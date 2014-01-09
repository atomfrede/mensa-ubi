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
package de.atomfrede.android.mensa.ubi.util;

import java.util.Calendar;

public class Util {

	public static int getWeekOfYear() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static int getYear() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}
	
	public static int getCacheKey() {
		int weekOfYear = getWeekOfYear();
		int year = getYear();
		
		int cacheKey = year + weekOfYear;
		return cacheKey;
	}
	
}
