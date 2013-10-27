package pl.tofilm.main_panel;

import pl.tofilm.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ToFilmMainPanelFragment extends Fragment {
	private static String LOG_CLASS_NAME = "ToFilmMainPanelFragment";
	private static TextView info;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.tab1_view, container, false);

		info = (TextView) V.findViewById(R.id.main_panel_info_tv);

		return V;
	}

	public static void setInfo(String value) {
		try {
			info.setText(value);
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "toString error: " + e.getMessage());
		}
	}
}
