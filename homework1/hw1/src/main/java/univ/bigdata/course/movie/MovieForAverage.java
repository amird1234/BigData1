package univ.bigdata.course.movie;

public class MovieForAverage extends Movie{
	private Integer numOfReviews;
	
	public MovieForAverage(String productId, double score){
		super(productId,score);
		numOfReviews = 0;
	}
	
	public MovieForAverage(String productId, double score, Integer nor){
		super(productId,score);
		numOfReviews = nor;
	}
	public void incNumOfReviews(){
		numOfReviews++;
	}
	
	public Integer getNumOfReviews(){
		return numOfReviews;
	}
	
	public void  addToMovieScore(double addedScore){
		super.setScore(super.getScore() + addedScore);
	}
	
	public double getAverage(){
		if(super.getProductId().equals("B00004CK40")){
			String s = "This";
			s = s+"1";
		}
		return super.getScore()/numOfReviews;
	}
}
