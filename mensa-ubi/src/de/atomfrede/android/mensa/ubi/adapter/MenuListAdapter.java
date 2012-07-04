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

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.data.Menu;

public class MenuListAdapter extends ArrayAdapter<Menu> {

	List<Menu> values;
	Context context;

	public MenuListAdapter(Context context, List<Menu> values) {
		super(context, R.layout.mensa_row_layout, values);
		this.context = context;
		this.values = values;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.mensa_row_layout, parent, false);
		TextView titleTxt = (TextView) rowView.findViewById(R.id.menu_title);
		TextView mealTxt = (TextView) rowView.findViewById(R.id.menu_text);
		TextView priceTxt = (TextView) rowView.findViewById(R.id.menu_prices);
		Menu currentMenu = values.get(position);
		titleTxt.setText(currentMenu.getTitle());
		mealTxt.setText(currentMenu.getText());
		
		priceTxt.setText(currentMenu.getPrice());
		if(currentMenu.getPrice() == null || currentMenu.getPrice().equals(""))
			priceTxt.setVisibility(View.GONE);
		return rowView;
	}
}
