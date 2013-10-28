package pl.tofilm;

import pl.tofilm.favorite_panel.ToFilmFavoritPanelFragment;
import pl.tofilm.loading_panel.ToFilmOnTaskCompletedInterface;
import pl.tofilm.main_panel.ToFilmMainPanelFragment;
import pl.tofilm.settings.ToFilmSettingsPanelFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

public class ToFilmMainFrgament extends ToFilmBaseFragmentActivity implements
		ToFilmOnTaskCompletedInterface {
	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Tab1"),
				ToFilmMainPanelFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Tab2"),
				ToFilmFavoritPanelFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Tab3"),
				ToFilmSettingsPanelFragment.class, null);

		ToFilmLogic.reloadProgramTable(this);
	}

	@Override
	public void onTaskPreparing() {
		createToastMessage("Preparing");
	}

	@Override
	public void onTaskCompleted() {
		createToastMessage("completed");
		ToFilmMainPanelFragment.setInfo(ToFilmLogic.showAllElementsInfo());
	}

	@Override
	public void hideKeyboard() {
		createToastMessage("hide keyboard");
	}
}