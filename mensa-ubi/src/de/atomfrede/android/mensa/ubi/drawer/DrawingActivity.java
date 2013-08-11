package de.atomfrede.android.mensa.ubi.drawer;

import java.util.List;

import net.simonvt.menudrawer.*;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.*;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.meal.WeeklyMealFragment_;

@OptionsMenu(R.menu.main)
@EActivity
public class DrawingActivity extends AbstractDrawingUbiActivity implements MenuAdapter.MenuListener {

	private static final String STATE_ACTIVE_POSITION = "net.simonvt.menudrawer.samples.LeftDrawerSample.activePosition";
	private static final String STATE_CURRENT_FRAGMENT = "net.simonvt.menudrawer.samples.FragmentSample";
	private static final String STATE_CURRENT_POSITION = "de.atomfrede.android.mensa.ubi.currentPosition";

	

	MenuAdapter mAdapter;
	ListView mList;

	FragmentManager mFragmentManager;
	FragmentTransaction mFragmentTransaction;

	/**
	 * The identifier of the currently display location, according to {@link Constants}
	 */
	int currentLocation = 0;
	/**
	 * The position marked inside the drawer.
	 */
	int mActivePosition = 1;
	String mCurrentFragmentTag;
	
	int mOldActivePosition = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
			mCurrentFragmentTag = savedInstanceState.getString(STATE_CURRENT_FRAGMENT);
			currentLocation = savedInstanceState.getInt(STATE_CURRENT_POSITION);
			mOldActivePosition = mActivePosition;
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		afterInject();
	}
	
	void afterInject() {
		setupDrawer();

		mFragmentManager = getSupportFragmentManager();

		if (mCurrentFragmentTag == null || (mCurrentFragmentTag != null && "".equals(mCurrentFragmentTag.trim()))) {
			// No fragment yet used, attach a new one
			mCurrentFragmentTag = ((Item) mAdapter.getItem(1)).mTitle;
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(mCurrentFragmentTag, 0, true), mCurrentFragmentTag);
			commitTransactions();
		}else{
			mCurrentFragmentTag = ((Item) mAdapter.getItem(mActivePosition)).mTitle;
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment("", currentLocation, true), mCurrentFragmentTag);
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
		
		maybePeekDrawer();
		
	}

	void setupActionbar(){
		
	}
	
	void maybePeekDrawer(){
		//Peek The Drawer for new Users...
				//mMenuDrawer.peekDrawer();
	}
	
	void setupDrawer(){
		mMenuDrawer = DraggableDrawer.attach(this, MenuDrawer.Type.BEHIND, getDrawerPosition(), getDragMode());
		
		//		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		
		
		List<MenuDrawerItem> items = setupListEntries();

		items.add(0, new Category(getResources().getString(R.string.drawer_head_uni)));
		items.add(3, new Category(getResources().getString(R.string.drawer_header_fh_bielefeld)));
		items.add(6, new Category(getResources().getString(R.string.drawer_header_hs_owl)));
		items.add(10, new Category(getResources().getString(R.string.drawer_header_musik)));

		mList = new ListView(this);
//		mList.setPadding(0, 53, 0, 0);

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
	}
	
	protected void onMenuItemClicked(int position, Item item) {
		if (position != mOldActivePosition) {
			if (mCurrentFragmentTag != null)
				detachFragment(getFragment(mCurrentFragmentTag, item.id, false));
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(item.mTitle, item.id, false), item.mTitle);
			mCurrentFragmentTag = item.mTitle;
			
		}
		currentLocation = item.id;
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
			Log.d(TAG, "OnItemClicked at Position "+position+" and id "+id);
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
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}

		super.onBackPressed();
	}
	
	@OptionsItem(R.id.menu_about)
	public void onMenuItemAbout() {
		showAboutDialog();
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
        outState.putString(STATE_CURRENT_FRAGMENT, mCurrentFragmentTag);
        outState.putInt(STATE_CURRENT_POSITION, currentLocation);
    }
}
