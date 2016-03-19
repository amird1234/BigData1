package comparators;

import java.util.Comparator;

import univ.bigdata.course.movie.MovieForAverage;

public class MovieReviewComparator implements Comparator<MovieForAverage>{

	@Override
	public int compare(MovieForAverage m1, MovieForAverage m2) {
		if((m2.getNumOfReviews()).equals(m1.getNumOfReviews())){
			return m1.getProductId().compareTo(m2.getProductId());
		}else{
			return m2.getNumOfReviews().compareTo(m1.getNumOfReviews()); 
		}
	}

}
