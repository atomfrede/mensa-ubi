package de.atomfrede.android.mensa.ubi.drawer;

import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.meal.WeeklyMealFragment_;

public abstract class AbstractDrawingUbiActivity extends SherlockFragmentActivity{

	protected static final String TAG = "DrawingActivity";
	
	protected MenuDrawer mMenuDrawer;
	protected boolean drawerAtLeastUsedOnce = false;
	protected SharedPreferences settings;
	protected boolean useStaticDrawer = false;
	
	FragmentManager mFragmentManager;
	
	/**
	 * The Spinner Location
	 */
	int selectedSpinnerValue = 0;
	
	/**
	 * The identifier of the currently display location, according to {@link Constants}
	 */
	int currentLocation = 0;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mMenuDrawer.toggleMenu();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	protected int getDragMode() {
		return MenuDrawer.MENU_DRAG_CONTENT;
	}

	protected Position getDrawerPosition() {
		return Position.LEFT;
	}
	
	protected int computeLocation(int positionClickedOn){
		if(selectedSpinnerValue == 0){
			return positionClickedOn;
		}else{
			return positionClickedOn + 10;
		}
	}
	
	protected void drawerUsed() {
		drawerAtLeastUsedOnce = true;
		settings.edit().putBoolean(Constants.drawerAtLeastUsedOnce, true).commit();
	}
	
	protected void maybePeekDrawer(){
		//Peek The Drawer for new Users...
				//mMenuDrawer.peekDrawer();
		settings = getSharedPreferences(Constants.MENSA_PREFS, AbstractDrawingUbiActivity.MODE_PRIVATE);
		drawerAtLeastUsedOnce = settings.getBoolean(Constants.drawerAtLeastUsedOnce, false);
		if(!drawerAtLeastUsedOnce){
			mMenuDrawer.peekDrawer();
		}
	}
	
	protected List<MenuDrawerItem> setupListEntries(){
		String[] locations = getResources().getStringArray(R.array.locations);
		List<MenuDrawerItem> items = new ArrayList<MenuDrawerItem>();

		int counter = 0;
		for (String loc : locations) {
			Item item = new Item(loc, R.drawable.ic_action_about, counter);
			items.add(item);
			counter++;
		}
		
		return items;
	}
	
	public void showAboutDialog() {
		Dialog dialog = new Dialog(this, getTheme(true));

		dialog.setContentView(R.layout.about_dialog);
		dialog.setTitle(getResources().getString(R.string.menu_about) + " " + getResources().getString(R.string.app_name));
		dialog.setCancelable(true);

		Button feedbackButton = (Button) dialog.findViewById(R.id.feedbackButton);
		feedbackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendFeedbackMail();
			}
		});

		String app_ver = "";
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.v(TAG, e.getMessage());
		}

		TextView versionName = (TextView) dialog.findViewById(R.id.textView1);
		versionName.setText("Version " + app_ver);
		
		((TextView)dialog.findViewById(R.id.textView8)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)dialog.findViewById(R.id.textView9)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)dialog.findViewById(R.id.textView10)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)dialog.findViewById(R.id.textView11)).setMovementMethod(LinkMovementMethod.getInstance());
		dialog.show();
	}

	private int getTheme(boolean light) {
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	        return light ? android.R.style.Theme_DeviceDefault_Light_Dialog : android.R.style.Theme_DeviceDefault_Dialog;
	    }
	    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	        return light ? android.R.style.Theme_Holo_Light_Dialog : android.R.style.Theme_Holo_Dialog;
	    }
	    else{
	        return android.R.style.Theme_Dialog;
	    }
	}
	
	public void sendFeedbackMail() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "atomfrede@gmail.com" });

		startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_provide_by)));
	}
	
	protected void setTitles(String newSubTitle){
		List<MenuDrawerItem> items = setupListEntries();
		switch (currentLocation) {
		case Constants.LOC_MENSA:
			newSubTitle = ((Item)items.get(0)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_head_uni));
			break;
		case Constants.LOC_WESTEND_RESTAURANT:
			newSubTitle = ((Item)items.get(1)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_head_uni));
			break;
		case Constants.LOC_KURT_SCHUHMACHER:
			newSubTitle = ((Item)items.get(2)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_header_fh_bielefeld));
			break;
		case Constants.LOC_WILHELM_BERTELSMANN:
			newSubTitle = ((Item)items.get(3)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_header_fh_bielefeld));
			break;
		case Constants.LOC_DETMOLD:
			newSubTitle = ((Item)items.get(4)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_header_hs_owl));
			break;
		case Constants.LOC_LEMGO:
			newSubTitle = ((Item)items.get(5)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_header_hs_owl));
			break;
		case Constants.LOC_HOEXTER:
			newSubTitle = ((Item)items.get(6)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_header_hs_owl));
			break;
		case Constants.LOC_MUSIC:
			newSubTitle = ((Item)items.get(7)).mTitle;
			getSupportActionBar().setTitle(getResources().getString(R.string.drawer_header_musik));
			break;
		default:
			break;
		}
		getSupportActionBar().setSubtitle(newSubTitle);
	}
	
	protected Fragment getFragment(String tag, int location, boolean force) {
		Fragment f = mFragmentManager.findFragmentByTag(tag);
		
		Log.d("MenuApdater", "Fragment found for tag f "+f+" and requested location "+location);
		if (f == null || force) {
			switch (location) {
			case Constants.LOC_MENSA:
				f = WeeklyMealFragment_.newInstance(Constants.MENSA_XML_KEY, Constants.mensaUrl, location);
				break;
			case Constants.LOC_MENSA_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.MENSA_NEXT_XML_KEY, Constants.mensaUrlNextWeek, location);
				break;
			case Constants.LOC_WESTEND_RESTAURANT:
				f = WeeklyMealFragment_.newInstance(Constants.WESTEND_RESTAURANT_XML_KEY, Constants.westendRestaurantUrl, location);
				break;
			case Constants.LOC_WESTEND_RESTAURANT_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.WESTEND_RESTAURANT_NEXT_XML_KEY, Constants.westendRestaurantUrlNextWeek, location);
				break;
			case Constants.LOC_DETMOLD:
				f = WeeklyMealFragment_.newInstance(Constants.DETMOLD_XML_KEY, Constants.detmoldUrl, location);
				break;
			case Constants.LOC_DETMOLD_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.DETMOLD_NEXT_XML_KEY, Constants.detmoldUrlNextWeek, location);
				break;
			case Constants.LOC_HOEXTER:
				f = WeeklyMealFragment_.newInstance(Constants.HOEXTER_XML_KEY, Constants.hoexterUrl, location);
				break;
			case Constants.LOC_HOEXTER_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.HOEXTER_NEXT_XML_KEY, Constants.hoexterUrlNextWeek, location);
				break;
			case Constants.LOC_KURT_SCHUHMACHER:
				f = WeeklyMealFragment_.newInstance(Constants.KURT_SCHUMACHER_XML_KEY, Constants.fhKurtSchumacherUrl, location);
				break;
			case Constants.LOC_KURT_SCHUHMACHER_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.KURT_SCHUMACHER_NEXT_XML_KEY, Constants.fhKurtSchumacherUrlNextWeek, location);
				break;
			case Constants.LOC_LEMGO:
				f = WeeklyMealFragment_.newInstance(Constants.LEMGO_XML_KEY, Constants.lemgoUrl, location);
				break;
			case Constants.LOC_LEMGO_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.LEMGO_NEXT_XML_KEY, Constants.lemgoUrlNextWeek, location);
				break;
			case Constants.LOC_WILHELM_BERTELSMANN:
				f = WeeklyMealFragment_.newInstance(Constants.WILHELM_BERTELSMANN_XML_KEY, Constants.wilhelmBerterlsmannUrl, location);
				break;
			case Constants.LOC_WILHELM_BERTELSMANN_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.WILHELM_BERTELSMANN_NEXT_XML_KEY, Constants.wilhelmBerterlsmannUrlNextWeek, location);
				break;
			case Constants.LOC_MUSIC:
				f = WeeklyMealFragment_.newInstance(Constants.MUSIC_XML_KEY, Constants.musicUrl, location);
				break;
			case Constants.LOC_MUSIC_NEXT_WEEK:
				f = WeeklyMealFragment_.newInstance(Constants.MUSIC_NEXT_XML_KEY, Constants.musicUrlNextWeek, location);
				break;
			default:
				break;
			}
		}
		return f;
	}
}
