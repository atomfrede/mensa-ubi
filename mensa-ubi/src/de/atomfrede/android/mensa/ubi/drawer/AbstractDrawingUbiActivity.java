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
import de.atomfrede.android.mensa.ubi.location.LocationSelectionActivity;

public abstract class AbstractDrawingUbiActivity extends SherlockFragmentActivity{

	protected static final String TAG = "DrawingActivity";
	
	protected MenuDrawer mMenuDrawer;
	protected boolean drawerAtLeastUsedOnce = false;
	protected SharedPreferences settings;
	protected boolean useStaticDrawer = false;
	
	/**
	 * The Spinner Location
	 */
	int selectedSpinnerValue = 0;
	
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
		settings = getSharedPreferences(Constants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);
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
}
