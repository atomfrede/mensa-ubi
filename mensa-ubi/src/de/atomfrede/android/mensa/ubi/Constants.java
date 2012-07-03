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
package de.atomfrede.android.mensa.ubi;

public class Constants {

	public static final int LOC_MENSA = 0;
	public static final int LOC_WESTEND_RESTAURANT = 1;
	public static final int LOC_KURT_SCHUHMACHER = 2;

	public static final int DAY_MONDAY = 0;
	public static final int DAY_TUESDAY = 1;
	public static final int DAY_WEDNESDAY = 2;
	public static final int DAY_THURSDAY = 3;
	public static final int DAY_FRIDAY = 4;

	public static String mensaUrl = "http://www.studentenwerkbielefeld.de/index.php?id=61";
	public static String westendRestaurantUrl = "http://www.studentenwerkbielefeld.de/index.php?id=60";
	public static String fhKurtSchumacherUrl = "http://www.studentenwerkbielefeld.de/index.php?id=63";

	public static final String LAST_UPDATE_KEY = "lastUpdate";
	public static final String MENSA_XML_KEY = "mensaXml";
	public static final String WESTEND_RESTAURANT_XML_KEY = "westendrestaurantXml";
	public static final String KURT_SCHUMACHER_XML_KEY = "kurtSchumacherXml";

	public static final String MENSA_PREFS = "mensaPrefs";
}
