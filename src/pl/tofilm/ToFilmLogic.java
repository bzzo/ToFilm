package pl.tofilm;

import java.util.ArrayList;
import java.util.List;

import pl.tofilm.loading_panel.ToFilmOnTaskCompletedInterface;
import pl.tofilm.loading_panel.ToFilmProgramLoader;

import android.util.Log;

public class ToFilmLogic {
	private static String LOG_CLASS_NAME = "ToFilmLogic";
	private static List<ToFilmProgramItem> programTable;

	public static void reloadProgramTable(ToFilmOnTaskCompletedInterface listener) {
		try {

			programTable = new ArrayList<ToFilmProgramItem>();

			ToFilmProgramLoader loader = new ToFilmProgramLoader(listener);
			loader.execute(programTable);

			listener.hideKeyboard();
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "loadTimeTable error: " + e.getMessage());
		}
	}

	public static final List<ToFilmProgramItem> getProgramTable() {
		return programTable;
	}

	public static String showAllElementsInfo() {
		String result = "";
		try {
			StringBuilder builder = new StringBuilder();
			for (ToFilmProgramItem item : programTable) {
				builder.append(item.getHourAndStation());
				builder.append(" ");
				builder.append(item.getTitle());
				builder.append(" ");
				builder.append(item.getTitleFull());
				builder.append("\n");
			}

			result = builder.toString();

		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "toString error: " + e.getMessage());
		}
		return result;
	}
}
