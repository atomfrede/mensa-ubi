package de.atomfrede.android.mensa.ubi.drawer;

public class Item {

	String mTitle;
    int mIconRes;

    Item(String title, int iconRes) {
        mTitle = title;
        mIconRes = iconRes;
    }
    
    Item(String title){
    	this(title, -1);
    }
}
