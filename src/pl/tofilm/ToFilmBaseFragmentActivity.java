package pl.tofilm;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class ToFilmBaseFragmentActivity extends FragmentActivity {
	private static String LOG_CLASS_NAME = "ToFilmBaseFragmentActivity";

	protected void createToastMessage(String text) {
		try {
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
					.show();

		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "createToastMessage error: " + e.getMessage());
		}
	}
}
