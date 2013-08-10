package de.atomfrede.android.mensa.ubi.meal;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

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
		loadData();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(MealPlan.getInstance().getMensaMenu() == null){
			//The application was resumed and before remove from memory so we need to restore the menu plans
			loadData();
		}
	}
	
	@UiThread
	public void onDataLoaded(){
		
		mAdapter = new WeekdayPagerAdapter(getActivity().getSupportFragmentManager(), weekdays, Constants.LOC_MENSA);
		mPager.setAdapter(mAdapter);

		mIndicator.setViewPager(mPager);
		mIndicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		
		loadingProgressbar.setVisibility(View.GONE);
		mPager.setVisibility(View.VISIBLE);
		mIndicator.setVisibility(View.VISIBLE);
	}
	
	@Background
	public void loadData(){
		try{
			
			switch (locationKey) {
			case 1:
				MealPlan.getInstance().setMensaMenu(Parser.parseMenu(true, settings.getString(xmlKey, null), settings, url, xmlKey));
				break;

			default:
				break;
			}
			onDataLoaded();
		}catch(Exception e){
			
		}
	}
}
