package univ.bigdata.course.providers;

import java.io.File;
import java.util.HashMap;

import univ.bigdata.course.movie.Movie;
import univ.bigdata.course.movie.MovieReview;

public class FileIOMoviesProvider implements MoviesProvider {
	
	String inputFile = null;
	HashMap<String, MovieReview> reviews = null;
	HashMap<String, Movie> movies = null;
	
	/**
	 * this function will parse inFile (the path to input) and will:
	 * - for each (new) movie - parse productID, create a Movie instance (and insert to "movies" hashmap) using productID.
	 * - for each review - parse all attributes, create a MovieReview instance (with all att's), insert to reviews hashmap. 
	 * @param inFile
	 */
	public FileIOMoviesProvider(String inFile) {
		//open file ...
		this.inputFile = inFile;
	}
	
    @Override
    public boolean hasMovie() {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }

    @Override
    public MovieReview getMovie() {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }
}
