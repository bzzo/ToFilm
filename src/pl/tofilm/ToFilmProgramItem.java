package pl.tofilm;

public class ToFilmProgramItem {
	public enum ProgramType {
		SUGGESTIONS, GENERAL, 
	};

	private String title = null;
	private String hourAndStation = null;
	private String image = null;
	private String link = null;
	private String titleFull = null;
	private ProgramType programType = null;

	public ToFilmProgramItem(String _title, String _hourAndStation,
			String _image, String _link, String _titleFull, ProgramType _programType) {

		title = _title;
		hourAndStation = _hourAndStation;
		image = _image;
		link = _link;
		titleFull = _titleFull;
		programType = _programType;
		
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

	public final ProgramType getProgramType()
	{
		return programType;
	}
}
