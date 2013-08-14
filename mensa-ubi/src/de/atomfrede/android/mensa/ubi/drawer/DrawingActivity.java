package de.atomfrede.android.mensa.ubi.drawer;

import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.OverlayDrawer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.actionbarsherlock.app.ActionBar;
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
	private static final String STATE_SELECT_SPINNER = "de,atomfrede.android.mensa.ubi.selctedSpinner";

	SpinnerAdapter mSpinnerAdapter;
	
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
			selectedSpinnerValue = savedInstanceState.getInt(STATE_SELECT_SPINNER);
			mOldActivePosition = mActivePosition;
		}
		
		useStaticDrawer = getResources().getString(R.string.use_static_drawer).equals("true");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		afterInject();
	}
	
	void afterInject() {
		setupDrawer();
		
		mFragmentManager = getSupportFragmentManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && !useStaticDrawer) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		mMenuDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
			@Override
			public void onDrawerStateChange(int oldState, int newState) {
				if (newState == MenuDrawer.STATE_CLOSED) {
					commitTransactions();
				}
				if(!drawerAtLeastUsedOnce){
					drawerUsed();
				}
			}

			@Override
			public void onDrawerSlide(float openRatio, int offsetPixels) {
				// Do nothing
			}
		});
		
		if(!useStaticDrawer){
			maybePeekDrawer();
		}
		
		setupActionbar();
		
		setTitles(null);
		
		mList.setSelection(mActivePosition);
		
		mList.performItemClick(mList, mActivePosition, mList.getItemIdAtPosition(mActivePosition));
		
		exchangeFragment();
		
	}

	void exchangeFragment(){
		if (mCurrentFragmentTag == null || (mCurrentFragmentTag != null && "".equals(mCurrentFragmentTag.trim()))) {
			// No fragment yet used, attach a new one
			mCurrentFragmentTag = ((Item) mAdapter.getItem(1)).mTitle;
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(mCurrentFragmentTag+"_0", 0, true), mCurrentFragmentTag);
			commitTransactions();
		}else{
			mCurrentFragmentTag = ((Item) mAdapter.getItem(mActivePosition)).mTitle+"_"+selectedSpinnerValue;
			Log.d("Fragment", "Current Fragment Tag "+mCurrentFragmentTag);
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(mCurrentFragmentTag, currentLocation, true), mCurrentFragmentTag);
			commitTransactions();
		}
	}
	
	void setupActionbar(){
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.mensa_dropdown,
		          android.R.layout.simple_spinner_dropdown_item);
		
		
		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				selectedSpinnerValue = itemPosition;
				int oldCurrentLocation = currentLocation;
				if(itemPosition == 0){
					//normal week
					//is normal week already selected -> do nothing
					if(currentLocation<10){
						return true;
					}else{
						currentLocation = currentLocation-10;
					}
					
				}else{
					//next week selected
					//is next week already selected -> do nothing
					if(currentLocation >= 10){
						return true;
					}else{
						currentLocation = currentLocation+10;
					}
				}
				if (mCurrentFragmentTag != null)
					detachFragment(getFragment(mCurrentFragmentTag, oldCurrentLocation, false));
				
				exchangeFragment();
				return true;
			}
		});
		getSupportActionBar().setSelectedNavigationItem(selectedSpinnerValue);
	}
	
	void setTitles(String newSubTitle){
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
	
	
	
	protected void setupDrawer(){
		if(useStaticDrawer){
			mMenuDrawer = OverlayDrawer.attach(this, MenuDrawer.Type.STATIC, getDrawerPosition(), getDragMode());
		}else{
			mMenuDrawer = OverlayDrawer.attach(this, MenuDrawer.Type.OVERLAY, getDrawerPosition(), getDragMode());
		}
		
		//		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		
		List<MenuDrawerItem> items = setupListEntries();

		items.add(0, new Category(getResources().getString(R.string.drawer_head_uni)));
		items.add(3, new Category(getResources().getString(R.string.drawer_header_fh_bielefeld)));
		items.add(6, new Category(getResources().getString(R.string.drawer_header_hs_owl)));
		items.add(10, new Category(getResources().getString(R.string.drawer_header_musik)));

		mList = new ListView(this);
//		mList.setPadding(0, 53, 0, 0);
		
		mList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		mAdapter = new MenuAdapter(this, items);
		mAdapter.setListener(this);
		mAdapter.setActivePosition(mActivePosition);

		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(mItemClickListener);

		mList.setSelection(mActivePosition);
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
			attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(item.mTitle+"_"+selectedSpinnerValue, computeLocation(item.id), true), item.mTitle);
			mCurrentFragmentTag = item.mTitle;
		}
		currentLocation = computeLocation(item.id);
		mOldActivePosition = position;
		setTitles(item.mTitle);
		mMenuDrawer.closeMenu();
		if(useStaticDrawer){
			commitTransactions();
		}
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
        outState.putInt(STATE_SELECT_SPINNER, selectedSpinnerValue);
    }
}
