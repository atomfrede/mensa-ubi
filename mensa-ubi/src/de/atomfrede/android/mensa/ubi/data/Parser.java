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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.SharedPreferences;
import android.util.Log;
import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.util.Util;

public class Parser {

	public final static String TAG = "Parser";

	public static WeeklyMenu parseMenu(boolean reload, String data, SharedPreferences settings, String url, String xmlKey) throws Exception {
		WeeklyMenu mensaMenu = new WeeklyMenu();
		Document doc;
		if (data != null && !data.equals("") && !reload){
			doc = Jsoup.parse(data);
		}else {
			doc = Jsoup.connect(url).get();
			String xmlData = doc.toString();
			settings.edit().putString(xmlKey, xmlData).commit();
		}

		Elements dailyBlocks = doc.select("div.day-block");

		for (Element dailyBlock : dailyBlocks) {
			Element dayInformation = dailyBlock.select("a.day-information").first();
			String day = dayInformation.text();
			mensaMenu.setWeekOfYear(Util.getWeekOfYear() + "");
			DailyMenu dailyMenu = new DailyMenu();
			dailyMenu.setDate(day);
			extractRow(dailyBlock, dailyMenu, day);
			mensaMenu.addDailyMenu(dailyMenu);

		}
		return mensaMenu;
	}

	private static void extractRow(Element dailyBlock, DailyMenu dailyMenu, String dayInformation) {
		Elements rows = dailyBlock.select("tr");

		for (Element row : rows) {
			Menu menu = new Menu();
			menu.setDate(dayInformation);
			String menuTitle = row.child(0).text();
			menu.setTitle(menuTitle);

			Elements importantElements = row.select("span.important");
			int impCounter = 0;
			if(!importantElements.isEmpty()){
				StringBuilder sb = new StringBuilder();
				for(Element important:importantElements){
					if(impCounter == 0)
						sb.append(important.text()+"\n\n");
					else if(impCounter == 1)
						sb.append(important.text()+" ");
					else 
						sb.append(important.text());
					impCounter++;
				}
				menu.setText(sb.toString());
				menu.setPrice("");
			}else {
				String menuText = row.child(1).child(0).text();
				menu.setText(menuText);

				Elements priceElements = row.child(2).select("p");
				StringBuilder sb = new StringBuilder();
				int counter = 0;
				for (Element priceElement : priceElements) {
					counter++;
					sb.append(priceElement.text());
					if (counter != priceElements.size())
						sb.append("\n");
				}
				menu.setPrice(sb.toString());
			}
			
			dailyMenu.addMenu(menu);
		}
	}
}
