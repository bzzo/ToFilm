package pl.tofilm.settings;

import pl.tofilm.R;
import pl.tofilm.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToFilmSettingsPanelFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.tab3_view, container, false);

		return V;
	}
}
