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

public class MealPlan implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8294145476387020583L;
	
	private static final MealPlan instance = new MealPlan();

	public static MealPlan getInstance() {
		return instance;
	}

	private WeeklyMenu mensaMenu;
	private WeeklyMenu westendRestauranMenu;
	private WeeklyMenu kurtSchuhmacherMenu;
	private WeeklyMenu wilhemBertelsmannMenu;
	private WeeklyMenu mindenMenu;
	private WeeklyMenu detmoldMenu;
	private WeeklyMenu lemgoMenu;
	private WeeklyMenu hoexterMenu;
	private WeeklyMenu musicMenu;
	
	private WeeklyMenu mensaMenuNext;

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

	public WeeklyMenu getWilhemBertelsmannMenu() {
		return wilhemBertelsmannMenu;
	}

	public void setWilhemBertelsmannMenu(WeeklyMenu wilhemBertelsmannMenu) {
		this.wilhemBertelsmannMenu = wilhemBertelsmannMenu;
	}

	public WeeklyMenu getMindenMenu() {
		return mindenMenu;
	}

	public void setMindenMenu(WeeklyMenu mindenMenu) {
		this.mindenMenu = mindenMenu;
	}

	public WeeklyMenu getDetmoldMenu() {
		return detmoldMenu;
	}

	public void setDetmoldMenu(WeeklyMenu detmoldMenu) {
		this.detmoldMenu = detmoldMenu;
	}

	public WeeklyMenu getLemgoMenu() {
		return lemgoMenu;
	}

	public void setLemgoMenu(WeeklyMenu lemgoMenu) {
		this.lemgoMenu = lemgoMenu;
	}

	public WeeklyMenu getHoexterMenu() {
		return hoexterMenu;
	}

	public void setHoexterMenu(WeeklyMenu hoexterMenu) {
		this.hoexterMenu = hoexterMenu;
	}

	public WeeklyMenu getMusicMenu() {
		return musicMenu;
	}

	public void setMusicMenu(WeeklyMenu musicMenu) {
		this.musicMenu = musicMenu;
	}

	public WeeklyMenu getMensaMenuNext() {
		return mensaMenuNext;
	}

	public void setMensaMenuNext(WeeklyMenu mensaMenuNext) {
		this.mensaMenuNext = mensaMenuNext;
	}
}
