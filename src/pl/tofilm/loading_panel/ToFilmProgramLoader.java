package pl.tofilm.loading_panel;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import pl.tofilm.ToFilmProgramItem;
import pl.tofilm.ToFilmProgramItem.ProgramType;
import pl.tofilm.ToFilmProgramItemComparator;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ToFilmProgramLoader extends
		AsyncTask<Object, Long, List<ToFilmProgramItem>> {
	private static String LOG_CLASS_NAME = "ToFilmProgramLoader";
	private ToFilmOnTaskCompletedInterface listener;
	private static int httpTimeout = 20 * 1000;
	private static String linkTodayNow = "http://www.teleman.pl/program-tv?cat=fil";
	private static String linkToday = "http://www.teleman.pl/program-tv?hour=%d&cat=fil";

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

			String linkActual = linkToday;

			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);

			for (int i = hour; i < 24; ++i) {
				String link = String.format(linkToday, i);
				Connection connection = Jsoup.connect(link)
						.timeout(httpTimeout);
				if (connection == null)
					return null;

				Document document = connection.get();
				if (document == null)
					return null;
				parseHtml(document, programTable);
			}

			//Collections.sort(programTable, new ToFilmProgramItemComparator());

			return programTable;
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "doInBackground error:" + e.getMessage());
		}
		return null;
	}

	private void parseGeneral(Document document,
			List<ToFilmProgramItem> programTable) {
		try {

			Element general = document
					.getElementById("stacje-ogolnotematyczne");
			if (general == null)
				return;

			Elements gridRows = general.getElementsByClass("grid-prog")
					.not(".pad_10").not(".pad_20");
			for (Element gridRow : gridRows) {
				if (gridRow == null)
					continue;

				String link = "";
				String dateTime = "";
				String titleFull = "";
				String time = "";
				String genre = "";

				Elements ellipsis = gridRow.getElementsByClass("ellipsis");
				if (ellipsis != null && ellipsis.size() > 0) {
					Element ellipsisElement = ellipsis.get(0);
					if (ellipsisElement != null) {
						link = ellipsisElement.getElementsByTag("a").attr(
								"href");
						dateTime = ellipsisElement.getElementsByTag("a").attr(
								"data-time");
						titleFull = ellipsisElement.getElementsByTag("a")
								.text();
					}
				}
				Elements gridInfo = gridRow.getElementsByClass("grid-info");
				if (gridInfo != null && gridInfo.size() > 0) {
					Element gridInfoElement = gridInfo.get(0);
					if (gridInfoElement != null) {
						Element timeElement = gridInfoElement
								.getElementsByClass("time").get(0);
						time = timeElement.text();

						Element genreElement = gridInfoElement
								.getElementsByClass("genre").get(0);
						genre = genreElement.text();

					}
				}
				String image = gridRow.getElementsByTag("img").attr("src");

				// String title = suggestionsTableElement
				// .getElementsByClass("title").first().text();
				// String hourAndStation = suggestionsTableElement
				// .getElementsByClass("airing").first().text();
				// String link = suggestionsTableElement.attr("href");
				// String titleFull = suggestionsTableElement.attr("title");

				ToFilmProgramItem element = new ToFilmProgramItem(titleFull,
						dateTime, time, image, link, titleFull, genre,
						ProgramType.GENERAL);

				boolean doInsert = true;
				for (ToFilmProgramItem item : programTable) {
					if (item.getLink().equals(element.getLink())) {
						doInsert = false;
						break;
					}
				}

				if (doInsert)
					programTable.add(element);

			}
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "parseSuggestions error:" + e.getMessage());
		}
	}

	private void parseSuggestions(Document document,
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

				ToFilmProgramItem element = new ToFilmProgramItem(title,
						hourAndStation, hourAndStation, image, link, titleFull, null,
						ProgramType.SUGGESTIONS);

				// if (element.getCityText().toUpperCase()
				// .startsWith(destinationPrefix))
				programTable.add(element);
				// }
			}
		} catch (Exception e) {
			Log.e(LOG_CLASS_NAME, "parseSuggestions error:" + e.getMessage());
		}
	}

	private void parseHtml(Document document,
			List<ToFilmProgramItem> programTable) {
		try {
			// parseSuggestions(document, programTable);
			parseGeneral(document, programTable);
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
