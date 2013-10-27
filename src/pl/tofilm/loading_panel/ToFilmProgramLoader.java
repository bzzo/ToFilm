package pl.tofilm.loading_panel;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import pl.tofilm.ToFilmProgramItem;
import pl.tofilm.ToFilmProgramItemComparator;

import android.os.AsyncTask;
import android.util.Log;

public class ToFilmProgramLoader extends
		AsyncTask<Object, Long, List<ToFilmProgramItem>> {
	private static String LOG_CLASS_NAME = "ToFilmProgramLoader";
	private ToFilmOnTaskCompletedInterface listener;
	private static int httpTimeout = 20 * 1000;
	private static String linkTodayNow = "http://www.teleman.pl/program-tv?cat=fil";

	// private static String linkBusIn =
	// "http://www.rda.krakow.pl/pl/przyjazdy.php";
	// private static String linkBusBeforeOut = "a[href^=odjazdy.php?]";
	// private static String linkBusBeforeIn = "a[href^=przyjazdy.php?]";

	public ToFilmProgramLoader(ToFilmOnTaskCompletedInterface listener) {
		this.listener = listener;
	}

	@Override
	protected List<ToFilmProgramItem> doInBackground(Object... params) {
		try {
			List<ToFilmProgramItem> programTable = (List<ToFilmProgramItem>) params[0];
			// Direction direction = (Direction) params[1];
			// String destinationPrefix = (String) params[2];

			String linkActual = linkTodayNow;
			// String linkActualBefore = direction == Direction.IN ?
			// linkBusBeforeIn
			// : linkBusBeforeOut;

			// if (direction == Direction.IN) {
			// ToRdaTimeTableItem.actualInCount = 0L;
			// ToRdaTimeTableItem.historicInCount = -1L
			// * maxHistoricItemCount;
			// } else if (direction == Direction.OUT) {
			// ToRdaTimeTableItem.actualOutCount = 0L;
			// ToRdaTimeTableItem.historicOutCount = -1L
			// * maxHistoricItemCount;
			// }

			Connection connection = Jsoup.connect(linkActual).timeout(
					httpTimeout);
			if (connection == null)
				return null;

			Document document = connection.get();
			if (document == null)
				return null;
			parseHtml(document, programTable);

			// Element elementLinkBefore =
			// document.select(linkActualBefore).first();
			// String relHref = linkBefore.attr("href"); // == "/"
			// String linkBefore = elementLinkBefore.attr("abs:href"); //
			// "http://jsoup.org/"

			// connection = Jsoup.connect(linkBefore).timeout(
			// ToRdaLogic.getHttpTimeout());
			// if (connection == null)
			// return null;

			// document = connection.get();
			// if (document == null)
			// return null;
			// parseHtml(document, false, timeTables, destinationPrefix,
			// direction);

			Collections.sort(programTable, new ToFilmProgramItemComparator());

			return programTable;
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "doInBackground error:" + e.getMessage());
		}
		return null;
	}

	private void parseHtml(Document document,
			List<ToFilmProgramItem> programTable) {
		try {
			// Elements tableInside =
			// document.getElementsByClass("suggestions");
			Element suggestionsTable = document.getElementById("suggestions");
			// if (tableInside == null || tableInside.size() == 0)
			// return;

			if (suggestionsTable == null)
				return;

			// Element timeTable = tableInside.get(0);
			// if (timeTable == null)
			// return;

			Elements suggestionsTableElements = suggestionsTable
					.getElementsByTag("a");

			if (suggestionsTableElements == null)
				return;

			for (Element suggestionsTableElement : suggestionsTableElements) {
				if (suggestionsTableElement == null)
					continue;

				// int startIndex = isActual ? 0 : (int)(rowsTdSize - 1 -
				// maxHistoricItemCount);
				// if (!isActual) {
				// if (rowsBody.indexOf(rowBody) < (rowsBody.size() -
				// maxHistoricItemCount)) {
				// continue;
				// }
				// }

				// Elements rowsTr =
				// suggestionsTableElement.getElementsByTag("li");
				// if (rowsTr == null)
				// continue;

				// for (Element rowTr : rowsTr) {
				// if (rowTr == null)
				// continue;

				// Elements rowsTd = rowTr.getElementsByTag("a");
				// if (rowsTd == null)
				// continue;
				//
				//
				// long rowsTdSize = rowsTd.size();

				String title = suggestionsTableElement
						.getElementsByClass("title").first().text();
				String hourAndStation = suggestionsTableElement
						.getElementsByClass("airing").first().text();
				String image = suggestionsTableElement.getElementsByTag("img")
						.attr("src");
				String link = suggestionsTableElement.attr("href");
				String titleFull = suggestionsTableElement.attr("title");

				//
				// for (int i = 0; i < rowsTdSize; ++i) {
				// switch (i) {
				// case 0: {
				// hour = rowsTd.get(i);
				// }
				// case 1: {
				// city = rowsTd.get(i);
				// }
				// case 2: {
				// company = rowsTd.get(i);
				// }
				// case 3: {
				// if (direction == Direction.IN) {
				// details = rowsTd.get(i);
				// } else if (direction == Direction.OUT) {
				// position = rowsTd.get(i);
				// }
				// }
				// case 4: {
				// if (direction == Direction.IN) {
				// // empty
				// } else if (direction == Direction.OUT) {
				// details = rowsTd.get(i);
				// }
				// }
				// }
				// }

				ToFilmProgramItem element = new ToFilmProgramItem(title, hourAndStation, image, link, titleFull);

				// if (element.getCityText().toUpperCase()
				// .startsWith(destinationPrefix))
				programTable.add(element);
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG_CLASS_NAME, "parseHtml error: " + e.getMessage());
		}
	}

	@Override
	protected void onPreExecute() {
		try {
			super.onPreExecute();
			listener.onTaskPreparing();
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "onPreExecute error:" + e.getMessage());
		}
	}

	@Override
	protected void onPostExecute(List<ToFilmProgramItem> result) {
		try {
			super.onPostExecute(result);

			listener.onTaskCompleted();
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "onPostExecute error:" + e.getMessage());
		}
	}
}
