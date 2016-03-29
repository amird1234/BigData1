package univ.bigdata.course.movie;

public class User {
	String userID;
	Double numOfReviews;

	public Double getNumOfReviews() {
		return numOfReviews;
	}

	public void setNumOfReviews(Double numOfReviews) {
		this.numOfReviews = numOfReviews;
	}
	
	public void incNumOfReviews() {
		this.numOfReviews++;
	}

	public String getUserID() {
		return userID;
	}

	public User(String userID, Double numOfReviews) {
		super();
		this.userID = userID;
		this.numOfReviews = numOfReviews;
	}

}
