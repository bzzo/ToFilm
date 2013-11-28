package pl.tofilm;

public class ToFilmProgramItem {
	public enum ProgramType {
		SUGGESTIONS, GENERAL,
	};

	private String title = null;
	private String dateTime = null;
	private String dateFrom = null;
	private String dateTo = null;
	private String time = null;
	private String image = null;
	private String link = null;
	private String titleFull = null;
	private String genre = null;
	private ProgramType programType = null;

	public ToFilmProgramItem(String _title, String _dateTime, String _time,
			String _image, String _link, String _titleFull, String _genre,
			ProgramType _programType) {

		title = _title;
		dateTime = _dateTime;
		dateFrom = dateTime.substring(0, dateTime.indexOf('-'));
		dateTo = dateTime.substring(dateTime.indexOf('-'), dateTime.length());
		time = _time;
		image = _image;
		link = _link;
		titleFull = _titleFull;
		genre = _genre;
		programType = _programType;

	}

	public final String getTitle() {
		return title;
	}

	public final String getDateTime() {
		return dateTime;
	}

	public final String getTime() {
		return time;
	}

	public final String getDateFrom() {
		return dateFrom;
	}

	public final String getDateTo() {
		return dateTo;
	}

	public final String getImage() {
		return image;
	}

	public final String getLink() {
		return link;
	}

	public final String getTitleFull() {
		return titleFull;
	}

	public final String getGenre() {
		return genre;
	}

	public final ProgramType getProgramType() {
		return programType;
	}

	public final String getAllData() {
		return title + dateTime + time + image + link + titleFull + genre
				+ programType.toString();
	}
}
