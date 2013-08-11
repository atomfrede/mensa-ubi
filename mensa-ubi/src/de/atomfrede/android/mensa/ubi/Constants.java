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
	public static final int LOC_WILHELM_BERTELSMANN = 3;
	public static final int LOC_DETMOLD = 4;
	public static final int LOC_LEMGO = 5;
	public static final int LOC_HOEXTER = 6;
	public static final int LOC_MUSIC = 7;
	
	public static final int LOC_MENSA_NEXT_WEEK = 10;
	public static final int LOC_WESTEND_RESTAURANT_NEXT_WEEK = 11;
	public static final int LOC_KURT_SCHUHMACHER_NEXT_WEEK = 12;
	public static final int LOC_WILHELM_BERTELSMANN_NEXT_WEEK = 13;
	public static final int LOC_DETMOLD_NEXT_WEEK = 14;
	public static final int LOC_LEMGO_NEXT_WEEK = 15;
	public static final int LOC_HOEXTER_NEXT_WEEK = 16;
	public static final int LOC_MUSIC_NEXT_WEEK = 17;

	public static final int DAY_MONDAY = 0;
	public static final int DAY_TUESDAY = 1;
	public static final int DAY_WEDNESDAY = 2;
	public static final int DAY_THURSDAY = 3;
	public static final int DAY_FRIDAY = 4;

	public static final String mensaUrl = "http://www.studentenwerkbielefeld.de/index.php?id=61";
	public static final String westendRestaurantUrl = "http://www.studentenwerkbielefeld.de/index.php?id=60";
	public static final String fhKurtSchumacherUrl = "http://www.studentenwerkbielefeld.de/index.php?id=63";
	public static final String wilhelmBerterlsmannUrl = "http://www.studentenwerkbielefeld.de/index.php?id=65";
	public static final String detmoldUrl = "http://www.studentenwerkbielefeld.de/index.php?id=69";
	public static final String lemgoUrl = "http://www.studentenwerkbielefeld.de/index.php?id=70&L=0";
	public static final String hoexterUrl = "http://www.studentenwerkbielefeld.de/index.php?id=71&L=0";
	public static final String musicUrl = "http://www.studentenwerkbielefeld.de/index.php?id=72&L=0";
	
	public static final String mensaUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=61&tx_studentenwerk_pi1[week]=1";
	public static final String westendRestaurantUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=60&tx_studentenwerk_pi1%5Bweek%5D=1";
	public static final String detmoldUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=69&L=0&tx_studentenwerk_pi1%5Bweek%5D=1";
	public static final String lemgoUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=70&tx_studentenwerk_pi1%5Bweek%5D=1";
	//TODO add correct urls
	public static final String hoexterUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=71&L=0";
	public static final String musicUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=72&L=0";
	public static final String fhKurtSchumacherUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=63";
	public static final String wilhelmBerterlsmannUrlNextWeek = "http://www.studentenwerkbielefeld.de/index.php?id=65";
	

	@Deprecated
	public static final String LAST_UPDATE_KEY = "lastUpdate";
	
	public static final String MENSA_XML_KEY = "mensaXml";
	public static final String MENSA_NEXT_XML_KEY = "mensaNextXml";
	
	public static final String WESTEND_RESTAURANT_XML_KEY = "westendrestaurantXml";
	public static final String WESTEND_RESTAURANT_NEXT_XML_KEY = "westendNextXml";
	
	public static final String KURT_SCHUMACHER_XML_KEY = "kurtSchumacherXml";
	public static final String KURT_SCHUMACHER_NEXT_XML_KEY = "kurtSchumacherNextXml";
	
	public static final String WILHELM_BERTELSMANN_XML_KEY = "wilhelmBertelsmannXml";
	public static final String WILHELM_BERTELSMANN_NEXT_XML_KEY = "wilhelmBertelsmannNextXml";
	
	public static final String DETMOLD_XML_KEY = "detmoldeXml";
	public static final String DETMOLD_NEXT_XML_KEY = "detmoldNextXml"; 
	
	public static final String LEMGO_XML_KEY = "lemgoXml";
	public static final String LEMGO_NEXT_XML_KEY = "lemgoNextXml";
	
	public static final String HOEXTER_XML_KEY = "hoexterXml";
	public static final String HOEXTER_NEXT_XML_KEY = "hoexterNextXml";
	
	public static final String MUSIC_XML_KEY = "musciXml";
	public static final String MUSIC_NEXT_XML_KEY = "musicNextXml";

	public static final String MENSA_PREFS = "mensaPrefs";
	
	public static final String MENSA_LAST_UPDATE = "mensaLastUpdate";
	public static final String MENSA_NEXT_LAST_UPDATE = "mensaNextLastUpdate";
	
	public static final String WESTEND_LAST_UDPATE = "westendLastUpdate";
	public static final String WESTEND_NEXT_LAST_UPDATE = "westendNextLastUpdate";
	
	public static final String KURT_SCHUHMACHER_LAST_UPDATE = "kurtSchuhmacherLastUpdate";
	public static final String KURT_SCHUMACHER_NEXT_LAST_UPDATE = "kurtSchumacherNextLastUpdate";
	
	public static final String BERTELSMANN_LAST_UPDATE = "bertelsmannLastUpdate";
	public static final String BERTELSMANN_NEXT_LAST_UPDATE = "bertelsmannNextLastUpdate";
	
	public static final String DETMOLD_LAST_UPDATE = "detmoldLastUpdate";
	public static final String DETMOLD_NEXT_LAST_UPDATE = "detmoldNextLastUpdate";
	
	public static final String LEMGO_LAST_UPDATE = "lemgoLastUpdate";
	public static final String LEMGO_NEXT_LAST_UPDATE = "lemgoNextLastUpdate";
	
	public static final String HOEXTER_LAST_UPDATE = "hoexterLastUpdate";
	public static final String HOEXTER_NEXT_LAST_UDAPTE = "hoexterNextLastUpdate";
	
	public static final String MUSIK_LAST_UPDATE = "musikLastUpdate";
	public static final String MUSIK_NEXT_LAST_UPDATE = "musikNextLastUpdate";
	
	public static final String drawerAtLeastUsedOnce = "drawerAtLeastUsedOnce";
	
}
