package pl.tofilm;

import java.util.Comparator;

public class ToFilmProgramItemComparator implements
		Comparator<ToFilmProgramItem> {

	@Override
	public int compare(ToFilmProgramItem lhs, ToFilmProgramItem rhs) {
		return lhs.getDateFrom().compareTo(rhs.getDateFrom());
	}

}
