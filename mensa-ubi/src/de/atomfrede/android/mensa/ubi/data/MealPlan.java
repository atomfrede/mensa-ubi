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

public class MealPlan {
	private static final MealPlan instance = new MealPlan();

	public static MealPlan getInstance() {
		return instance;
	}

	private WeeklyMenu mensaMenu;
	private WeeklyMenu westendRestauranMenu;
	private WeeklyMenu kurtSchuhmacherMenu;

	private MealPlan() {
	}

	public WeeklyMenu getMensaMenu() {
		return mensaMenu;
	}

	public void setMensaMenu(WeeklyMenu mensaMenu) {
		this.mensaMenu = mensaMenu;
	}

	public WeeklyMenu getWestendRestauranMenu() {
		return westendRestauranMenu;
	}

	public void setWestendRestauranMenu(WeeklyMenu westendRestauranMenu) {
		this.westendRestauranMenu = westendRestauranMenu;
	}

	public WeeklyMenu getKurtSchuhmacherMenu() {
		return kurtSchuhmacherMenu;
	}

	public void setKurtSchuhmacherMenu(WeeklyMenu kurtSchuhmacherMenu) {
		this.kurtSchuhmacherMenu = kurtSchuhmacherMenu;
	}

}
