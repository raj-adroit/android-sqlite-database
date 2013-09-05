package com.flywithcode.lakshmiagencies;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.os.Bundle;

public class MainTabs extends Activity {
	Tab tab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create the actionbar
		ActionBar actionBar = getActionBar();

		// Hide Actionbar Icon
		actionBar.setDisplayShowHomeEnabled(false);

		// Hide Actionbar Title
		actionBar.setDisplayShowTitleEnabled(false);

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create first Tab
		tab = actionBar.newTab().setTabListener(new CustomerTab());
		// Create your own custom icon
		//tab.setIcon(R.drawable.tab1);
		tab.setText("Customers");
		actionBar.addTab(tab);

		/*// Create Second Tab
		tab = actionBar.newTab().setTabListener(new FragmentsTab2());
		// Set Tab Title
		tab.setText("Tab2");
		actionBar.addTab(tab);

		// Create Third Tab
		tab = actionBar.newTab().setTabListener(new FragmentsTab3());
		// Set Tab Title
		tab.setText("Tab3");
		actionBar.addTab(tab);*/
	}
}
