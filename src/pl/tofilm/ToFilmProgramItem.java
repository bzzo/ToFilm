package pl.tofilm;

public class ToFilmProgramItem {
	private String title = null;
	private String hourAndStation = null;
	private String image = null;
	private String link = null;
	private String titleFull = null;

	public ToFilmProgramItem(String _title, String _hourAndStation,
			String _image, String _link, String _titleFull) {

		title = _title;
		hourAndStation = _hourAndStation;
		image = _image;
		link = _link;
		titleFull = _titleFull;

	}

	public final String getTitle() {
		return title;
	}

	public final String getHourAndStation() {
		return hourAndStation;
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
	
	
	
}
