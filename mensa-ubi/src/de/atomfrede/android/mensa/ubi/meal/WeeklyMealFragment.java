package de.atomfrede.android.mensa.ubi.meal;



import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.googlecode.androidannotations.annotations.*;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.adapter.WeekdayPagerAdapter;
import de.atomfrede.android.mensa.ubi.data.MealPlan;
import de.atomfrede.android.mensa.ubi.data.Parser;
import de.atomfrede.android.mensa.ubi.location.LocationSelectionActivity;
import de.atomfrede.android.mensa.ubi.util.Util;

@EFragment(R.layout.fragment_weekly_meal)
public class WeeklyMealFragment extends SherlockFragment {

	public static WeeklyMealFragment_ newInstance(String key, String url, int locationKey) {
		WeeklyMealFragment_ f = new WeeklyMealFragment_();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("locationKey", locationKey);
		args.putString("key", key);
		args.putString("url", url);
		f.setArguments(args);

		return f;
	}
	
	static final String TAG = "WeeklyMealFragment";
	
	protected String[] weekdays;

	protected WeekdayPagerAdapter mAdapter;
	
	protected SharedPreferences settings;
	
	@FragmentArg("key")
	public String xmlKey;
	
	@FragmentArg("url")
	public String url;
	
	@FragmentArg("locationKey")
	public int locationKey;
	
	@ViewById(R.id.refresh_progress_bar)
	protected ProgressBar loadingProgressbar;
	
	@ViewById(R.id.meal_pager)
	protected ViewPager mPager;
	
	@ViewById(R.id.day_indicator)
	protected TitlePageIndicator mIndicator;
	
	@ViewById(R.id.important_info)
	protected RelativeLayout importanWrapper;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		weekdays = getResources().getStringArray(R.array.weekdays_short);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		settings = getActivity().getSharedPreferences(Constants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);
	}
	
	@AfterViews
	public void afterViews(){
		Log.d(TAG, "AfterViews");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.d(TAG, "onResume");
		switch (locationKey) {
		case Constants.LOC_MENSA:{
			if(MealPlan.getInstance().getMensaMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		case Constants.LOC_WESTEND_RESTAURANT:{
			if(MealPlan.getInstance().getWestendRestauranMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		case Constants.LOC_DETMOLD:{
			if(MealPlan.getInstance().getDetmoldMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		case Constants.LOC_HOEXTER:{
			if(MealPlan.getInstance().getHoexterMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		case Constants.LOC_KURT_SCHUHMACHER:{
			if(MealPlan.getInstance().getKurtSchuhmacherMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		case Constants.LOC_LEMGO:{
			if(MealPlan.getInstance().getLemgoMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		case Constants.LOC_MUSIC:{
			if(MealPlan.getInstance().getMusicMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		case Constants.LOC_WILHELM_BERTELSMANN:{
			if(MealPlan.getInstance().getWilhemBertelsmannMenu() == null){
				//The application was resumed and before remove from memory so we need to restore the menu plans
				loadData();
			}else{
				onDataLoaded();
			}
			break;
		}
		default:
			break;
		}
		
		
	}
	
	@UiThread
	public void onDataLoaded(){
		Log.d(TAG, "OnDataLoaded...");
		
		if(mealsAvailable()){
			//getDailyMenues
			mAdapter = new WeekdayPagerAdapter(getActivity().getSupportFragmentManager(), weekdays, locationKey);
			mPager.setAdapter(mAdapter);
	
			mIndicator.setViewPager(mPager);
			mIndicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
			
			loadingProgressbar.setVisibility(View.GONE);
			Log.d(TAG, "Progress gone");
			selectInitialDay();
			mPager.setVisibility(View.VISIBLE);
			mIndicator.setVisibility(View.VISIBLE);
		}else{
			loadingProgressbar.setVisibility(View.GONE);
			importanWrapper.setVisibility(View.VISIBLE);
		}
		
	}
	
	public boolean mealsAvailable(){
		switch (locationKey) {
		case Constants.LOC_MENSA:{
			return MealPlan.getInstance().getMensaMenu().getDailyMenues().size() != 0;
		}case Constants.LOC_DETMOLD:{
			return MealPlan.getInstance().getDetmoldMenu().getDailyMenues().size() != 0;
		}case Constants.LOC_HOEXTER:{
			return MealPlan.getInstance().getHoexterMenu().getDailyMenues().size() != 0;
		}case Constants.LOC_KURT_SCHUHMACHER:{
			return MealPlan.getInstance().getKurtSchuhmacherMenu().getDailyMenues().size() != 0;
		}case Constants.LOC_LEMGO:{
			return MealPlan.getInstance().getLemgoMenu().getDailyMenues().size() != 0;
		}case Constants.LOC_MUSIC:{
			return MealPlan.getInstance().getMusicMenu().getDailyMenues().size() != 0;
		}case Constants.LOC_WESTEND_RESTAURANT:{
			return MealPlan.getInstance().getWestendRestauranMenu().getDailyMenues().size() != 0;
		}case Constants.LOC_WILHELM_BERTELSMANN:{
			return MealPlan.getInstance().getWilhemBertelsmannMenu().getDailyMenues().size() != 0;
		}
		default:
			return false;
		}
	}
	
	@Background
	public void loadData(){
		Log.d(TAG, "loadData...");
		try{
			switch (locationKey) {
			case Constants.LOC_MENSA:{
				boolean updateRequired = updateRequired(Constants.MENSA_LAST_UPDATE);
				Log.d(TAG, "Update Required = "+updateRequired);
				MealPlan.getInstance().setMensaMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				Log.d(TAG, "Loading done...");
				settings.edit().putInt(Constants.MENSA_LAST_UPDATE, Util.getWeekOfYear()).commit();
				break;
			}case Constants.LOC_WESTEND_RESTAURANT:{
				boolean updateRequired = updateRequired(Constants.WESTEND_LAST_UDPATE);
				MealPlan.getInstance().setWestendRestauranMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				settings.edit().putInt(Constants.WESTEND_LAST_UDPATE, Util.getWeekOfYear()).commit();
				break;
			}case Constants.LOC_DETMOLD:{
				boolean updateRequired = updateRequired(Constants.DETMOLD_LAST_UPDATE);
				MealPlan.getInstance().setDetmoldMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				settings.edit().putInt(Constants.DETMOLD_LAST_UPDATE, Util.getWeekOfYear()).commit();
				break;
			}case Constants.LOC_HOEXTER:{
				boolean updateRequired = updateRequired(Constants.HOEXTER_LAST_UPDATE);
				MealPlan.getInstance().setHoexterMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				settings.edit().putInt(Constants.HOEXTER_LAST_UPDATE, Util.getWeekOfYear()).commit();
				break;
			}case Constants.LOC_KURT_SCHUHMACHER:{
				boolean updateRequired = updateRequired(Constants.KURT_SCHUHMACHER_LAST_UPDATE);
				MealPlan.getInstance().setKurtSchuhmacherMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				settings.edit().putInt(Constants.KURT_SCHUHMACHER_LAST_UPDATE, Util.getWeekOfYear()).commit();
				break;
			}case Constants.LOC_LEMGO:{
				boolean updateRequired = updateRequired(Constants.LEMGO_LAST_UPDATE);
				MealPlan.getInstance().setLemgoMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				settings.edit().putInt(Constants.LEMGO_LAST_UPDATE, Util.getWeekOfYear()).commit();
				break;
			}case Constants.LOC_MUSIC:{
				boolean updateRequired = updateRequired(Constants.MUSIK_LAST_UPDATE);
				MealPlan.getInstance().setMusicMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				settings.edit().putInt(Constants.MUSIK_LAST_UPDATE, Util.getWeekOfYear()).commit();
				break;
			}case Constants.LOC_WILHELM_BERTELSMANN:{
				boolean updateRequired = updateRequired(Constants.BERTELSMANN_LAST_UPDATE);
				MealPlan.getInstance().setWilhemBertelsmannMenu(Parser.parseMenu(updateRequired, settings.getString(xmlKey, null), settings, url, xmlKey));
				settings.edit().putInt(Constants.BERTELSMANN_LAST_UPDATE, Util.getWeekOfYear()).commit();
				break;
			}
			
			
			default:
				break;
			}
			onDataLoaded();
		}catch(Exception e){
			Log.e(TAG, "Error while loading Data..."+e);
		}
	}
	
	private boolean updateRequired(String lastUpdateKey){
		if(!settings.contains(lastUpdateKey)){
			return true;
		}
		
		int lastUpdate = settings.getInt(lastUpdateKey, -1);
		if(Util.getWeekOfYear() > lastUpdate){
			return true;
		}

		return false;
	}
	
	protected void selectInitialDay() {
		Calendar today = Calendar.getInstance();
		int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 2:
			mIndicator.setCurrentItem(0);
			break;
		case 3:
			mIndicator.setCurrentItem(1);
			break;
		case 4:
			mIndicator.setCurrentItem(2);
			break;
		case 5:
			mIndicator.setCurrentItem(3);
			break;
		case 6:
			mIndicator.setCurrentItem(4);
			break;
		default:
			mIndicator.setCurrentItem(0);
			break;
		}
	}
}
