package de.atomfrede.android.mensa.ubi.drawer;

public class Category extends MenuDrawerItem{

	String mTitle;

    Category(String title) {
        mTitle = title;
        id = (int)(Math.random()*((49-1) + 1))+1;
    }
}
