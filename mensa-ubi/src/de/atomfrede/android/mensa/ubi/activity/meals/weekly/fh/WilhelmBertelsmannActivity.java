package de.atomfrede.android.mensa.ubi.activity.meals.weekly.fh;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

import de.atomfrede.android.mensa.ubi.Constants;
import de.atomfrede.android.mensa.ubi.R;
import de.atomfrede.android.mensa.ubi.activity.meals.weekly.AbstractWeeklyMenuActivity;
import de.atomfrede.android.mensa.ubi.adapter.WeekdayPagerAdapter;

public class WilhelmBertelsmannActivity extends AbstractWeeklyMenuActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(getResources().getString(R.string.wilhelm_bertelsmann_title));

		mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new WeekdayPagerAdapter(getSupportFragmentManager(), weekdays, Constants.LOC_WILHELM_BERTELSMANN);
		mPager.setAdapter(mAdapter);

		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		mIndicator = indicator;

		selectInitialDay();
	}
}
