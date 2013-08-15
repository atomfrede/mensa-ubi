package de.atomfrede.android.mensa.ubi.drawer;

public class Item extends MenuDrawerItem{

	String mTitle;
    int mIconRes;

    Item(String title, int iconRes, int locationId) {
        mTitle = title;
        mIconRes = iconRes;
        id = locationId;
    }
    
    Item(String title){
    	this(title, -1, -1);
    }
}
