package de.atomfrede.android.mensa.ubi.activity.meals.weekly;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.adapter.WeekdayPagerAdapter;

public class WestendRestaurantActivity extends AbstractWeeklyMenuActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(getResources().getString(R.string.westend_title));

		mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new WeekdayPagerAdapter(getSupportFragmentManager(), weekdays, Constants.LOC_WESTEND_RESTAURANT);
		mPager.setAdapter(mAdapter);

		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		mIndicator = indicator;

		selectInitialDay();
	}
}
