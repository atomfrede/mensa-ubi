package de.atomfrede.android.mensa.ubi.drawer;

import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.*;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.meal.WeeklyMealFragment_;

@OptionsMenu(R.menu.main)
@EActivity
public class DrawingActivity extends SherlockFragmentActivity implements MenuAdapter.MenuListener {

	private static final String TAG = "DrawingActivity";
	private static final String STATE_ACTIVE_POSITION = "net.simonvt.menudrawer.samples.LeftDrawerSample.activePosition";
	private static final String STATE_CURRENT_FRAGMENT = "net.simonvt.menudrawer.samples.FragmentSample";
	private static final String STATE_CURRENT_POSITION = "de.atomfrede.android.mensa.ubi.currentPosition";

	MenuDrawer mMenuDrawer;

	MenuAdapter mAdapter;
	ListView mList;

	FragmentManager mFragmentManager;
	FragmentTransaction mFragmentTransaction;

	int currentPosition = 0;
	int mActivePosition = 0;
	String mCurrentFragmentTag;
	
	int mOldActivePosition = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
			mCurrentFragmentTag = savedInstanceState.getString(STATE_CURRENT_FRAGMENT);
			currentPosition = savedInstanceState.getInt(STATE_CURRENT_POSITION);
			mOldActivePosition = mActivePosition;
		}

//		afterInject();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		afterInject();
	}
	void afterInject() {
		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, getDrawerPosition(), getDragMode());
		
//		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		
		String[] locations = getResources().getStringArray(R.array.locations);
		List<Object> items = new ArrayList<Object>();

		for (String loc : locations) {
			Item item = new Item(loc, R.drawable.ic_action_about);
			items.add(item);
		}

		mList = new ListView(this);
		mList.setPadding(0, 53, 0, 0);

		mAdapter = new MenuAdapter(this, items);
		mAdapter.setListener(this);
		mAdapter.setActivePosition(mActivePosition);

		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(mItemClickListener);

		mMenuDrawer.setMenuView(mList);

		// The drawable that replaces the up indicator in the action bar
		mMenuDrawer.setSlideDrawable(R.drawable.ic_drawer);
		// Whether the previous drawable should be shown
		mMenuDrawer.setDrawerIndicatorEnabled(true);

		mFragmentManager = getSupportFragmentManager();

		if (mCurrentFragmentTag == null || (mCurrentFragmentTag != null && "".equals(mCurrentFragmentTag.trim()))) {
			// No fragment yet used, attach a new one
			mCurrentFragmentTag = ((Item) mAdapter.getItem(0)).mTitle;
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(mCurrentFragmentTag, 0, true), mCurrentFragmentTag);
			commitTransactions();
		}else{
			mCurrentFragmentTag = ((Item) mAdapter.getItem(0)).mTitle;
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment("", mActivePosition, true), mCurrentFragmentTag);
			commitTransactions();
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		mMenuDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
			@Override
			public void onDrawerStateChange(int oldState, int newState) {
				if (newState == MenuDrawer.STATE_CLOSED) {
					commitTransactions();
				}
			}

			@Override
			public void onDrawerSlide(float openRatio, int offsetPixels) {
				// Do nothing
			}
		});

	}

	protected void onMenuItemClicked(int position, Item item) {
		if (position != mOldActivePosition) {
			if (mCurrentFragmentTag != null)
				detachFragment(getFragment(mCurrentFragmentTag, position, false));
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(item.mTitle, position, false), item.mTitle);
			mCurrentFragmentTag = item.mTitle;
			
		}
		mOldActivePosition = position;
		mMenuDrawer.closeMenu();
	}

	protected int getDragMode() {
		return MenuDrawer.MENU_DRAG_CONTENT;
	}

	protected Position getDrawerPosition() {
		return Position.LEFT;
	}

	protected FragmentTransaction ensureTransaction() {
		if (mFragmentTransaction == null) {
			mFragmentTransaction = mFragmentManager.beginTransaction();
			mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}

		return mFragmentTransaction;
	}

	private Fragment getFragment(String tag, int location, boolean force) {
		Fragment f = mFragmentManager.findFragmentByTag(tag);
		
		Log.d("MenuApdater", "Fragment found for tag f "+f);
		if (f == null || force) {
			if(location == Constants.LOC_MENSA){
				f = WeeklyMealFragment_.newInstance(Constants.MENSA_XML_KEY, Constants.mensaUrl, location);
			}
			if(location == Constants.LOC_WESTEND_RESTAURANT){
				f = WeeklyMealFragment_.newInstance(Constants.WESTEND_RESTAURANT_XML_KEY, Constants.westendRestaurantUrl, location);
			}
			
		}
		return f;
	}

	protected void attachFragment(int layout, Fragment f, String tag) {
		if (f != null) {
			if (f.isDetached()) {
				ensureTransaction();
				mFragmentTransaction.attach(f);
			} else if (!f.isAdded()) {
				ensureTransaction();
				mFragmentTransaction.add(layout, f, tag);
			}
		}
	}

	protected void detachFragment(Fragment f) {
		if (f != null && !f.isDetached()) {
			ensureTransaction();
			mFragmentTransaction.detach(f);
		}
	}

	protected void commitTransactions() {
		if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
			mFragmentTransaction.commit();
			mFragmentTransaction = null;
		}
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mOldActivePosition = mActivePosition;
			mActivePosition = position;
			mMenuDrawer.setActiveView(view, position);
			mAdapter.setActivePosition(position);
			onMenuItemClicked(position, (Item) mAdapter.getItem(position));
		}
	};

	@Override
	public void onActiveViewChanged(View v) {
		Log.d("MenuAdapter", "onActiveViewChange with postion "+mActivePosition);
		mMenuDrawer.setActiveView(v, mActivePosition);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mMenuDrawer.toggleMenu();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}

		super.onBackPressed();
	}
	
	@OptionsItem(R.id.menu_about)
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
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
        outState.putString(STATE_CURRENT_FRAGMENT, mCurrentFragmentTag);
        outState.putInt(STATE_CURRENT_POSITION, currentPosition);
    }
}
