package univ.bigdata.course;

import univ.bigdata.course.movie.Movie;
import univ.bigdata.course.movie.MovieForAverage;
import univ.bigdata.course.movie.MovieReview;
import univ.bigdata.course.providers.MoviesProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import comparators.MovieReviewComparator;
import comparators.MovieScoreComparator;

/**
 * Main class which capable to keep all information regarding movies review.
 * Has to implements all methods from @{@link IMoviesStorage} interface.
 * Also presents functionality to answer different user queries, such as:
 * <p>
 * 1. Total number of distinct movies reviewed.
 * 2. Total number of distinct users that produces the review.
 * 3. Average review score for all movies.
 * 4. Average review score per single movie.
 * 5. Most popular movie reviewed by at least "K" unique users
 * 6. Word count for movie review, select top "K" words
 * 7. K most helpful users
 */
public class MoviesStorage implements IMoviesStorage {
	MoviesProvider localProvider;
	
    public MoviesStorage(final MoviesProvider provider) {
        //TODO: read movies using provider interface
       this.localProvider = provider;
    }

    @Override
    public double totalMoviesAverageScore() {
    	Double Average = 0.0;
    	Integer NumOfReviewForMovie = 0;
    	
    	//Sum all the scores for all the movies
        while(localProvider.hasMovie()){
        	MovieReview mr = localProvider.getMovie();
        	if(mr != null){
        		Average += mr.getMovie().getScore();
        		NumOfReviewForMovie++;
        	}
        }
        Average = Average/NumOfReviewForMovie;
        return Average;
    }

    @Override
    public double totalMovieAverage(String productId) {
    	Double Average = 0.0;
    	Integer NumOfReviewForMovie = 0;
    	
    	//Sum all the scores for the specific movie
        while(localProvider.hasMovie()){
        	MovieReview mr = localProvider.getMovie();
        	if(mr != null){
        		if(mr.getMovie().getProductId().equals(productId)){
        			Average += mr.getMovie().getScore();
        			NumOfReviewForMovie++;
        		}
        	}
        }
        Average = Average/NumOfReviewForMovie;
        return Average;
    }

    @Override
    public List<Movie> getTopKMoviesAverage(long topK) {
    	int i;
    	List<Movie> returnedList = new ArrayList<>();
    	List<Movie> tempMovieList = new ArrayList<>();
    	tempMovieList = getMoviesAverage();
    	tempMovieList.sort(new MovieScoreComparator());
    	for(i=0 ; i<topK ; i++){
    		returnedList.add(tempMovieList.get(i));
    	}
    	return returnedList;
    }

    @Override
    public Movie movieWithHighestAverage() {
        List<Movie> movArray = getTopKMoviesAverage(1);
        return movArray.get(0);
    }

    @Override
    public List<Movie> getMoviesPercentile(double percentile) {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }

    @Override
    public String mostReviewedProduct() {
        Map<String, Long> tempMap = reviewCountPerMovieTopKMovies(1);
        String returnedString = null;
        for(String s : tempMap.keySet()){
        	returnedString = s;
        }
        return returnedString;
    }

    @Override
    public Map<String, Long> reviewCountPerMovieTopKMovies(int topK) {
    	List<MovieForAverage> movieList = new ArrayList<>();
    	HashMap<String, MovieForAverage> aveMap = new HashMap<>();
    	Map<String, Long> returnedMap = new LinkedHashMap<>();
    	int i;
    	 while(localProvider.hasMovie()){
         	MovieReview mr = localProvider.getMovie();
         	if(mr != null){
         		MovieForAverage movForAve = aveMap.get(mr.getMovie().getProductId());
         		//if movie already exist in hash map.
         		if(movForAve != null){
         			//update movie number of reviews.
         			movForAve.incNumOfReviews();
         		}else{
         			//create a new MovieForAverage instance and add it to HashMap.
         			movForAve = new MovieForAverage(mr.getMovie().getProductId(), mr.getMovie().getScore());
         			movForAve.incNumOfReviews();
         			aveMap.put(mr.getMovie().getProductId(), movForAve);
         		}
         	}
         }
    	 //Goes over all the movies, calculates their average and adds them to the returned list.
    	 for (MovieForAverage value : aveMap.values()) {
//    		    MovieForAverage tempM = new MovieForAverage(value.getProductId(), value.getAverage());
    		    movieList.add(value);
    	 }
    	 movieList.sort(new MovieReviewComparator());
    	 for(i=0 ; i<topK ; i++){
    		 MovieForAverage tempMFA = movieList.get(i);
    		 returnedMap.put(tempMFA.getProductId(), (new Long(tempMFA.getNumOfReviews())));
    	 }
    	return returnedMap; 
    }

    @Override
    public String mostPopularMovieReviewedByKUsers(int numOfUsers) {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }

    @Override
    public Map<String, Long> moviesReviewWordsCount(int topK) {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }

    @Override
    public Map<String, Long> topYMoviewsReviewTopXWordsCount(int topMovies, int topWords) {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }

    @Override
    public Map<String, Double> topKHelpfullUsers(int k) {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }

    @Override
    public long moviesCount() {
        throw new UnsupportedOperationException("You have to implement this method on your own.");
    }
    
    /***
     * This function calculates the average score for the movies in the provider
     * @return A list of Movie objects each holding its average score in the score variable. 
     */
    private List<Movie> getMoviesAverage(){
    	List<Movie> returnedList = new ArrayList<>();
    	HashMap<String, MovieForAverage> aveMap = new HashMap<>();
    	 while(localProvider.hasMovie()){
         	MovieReview mr = localProvider.getMovie();
         	if(mr != null){
         		MovieForAverage movForAve = aveMap.get(mr.getMovie().getProductId());
         		//if movie already exist in hash map.
         		if(movForAve != null){
         			//update movie score sum and number of reviews.
         			movForAve.addToMovieScore(mr.getMovie().getScore());
         			movForAve.incNumOfReviews();
         		}else{
         			//create a new MovieForAverage instance and add it to HashMap.
         			movForAve = new MovieForAverage(mr.getMovie().getProductId(), mr.getMovie().getScore());
         			movForAve.incNumOfReviews();
         			aveMap.put(mr.getMovie().getProductId(), movForAve);
         		}
         	}
         }
    	 //Goes over all the movies, calculates their average and adds them to the returned list.
    	 for (MovieForAverage value : aveMap.values()) {
    		    Movie tempM = new Movie(value.getProductId(), value.getAverage());
    		    returnedList.add(tempM);
    	 }
    	return returnedList; 
    }
}
