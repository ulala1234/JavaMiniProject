package model;
import java.util.Calendar;

public class StarRatingDTO {
    // 필드
    private int id;
    private int movieId;
    private int writer;
    private int rating;
    private String review;
    private Calendar writtenDate;
    
    // 게터세터
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public int getWriter() {
        return writer;
    }
    public void setWriter(int writer) {
        this.writer = writer;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public Calendar getWrittenDate() {
        return writtenDate;
    }
    public void setWrittenDate(Calendar writtenDate) {
        this.writtenDate = writtenDate;
    }
    
    // equals() 오버라이드
    public boolean equals(Object o) {
        if(o instanceof StarRatingDTO) {
            StarRatingDTO s = (StarRatingDTO) o;
            return id == s.id;
        }
        
        return false;
    }
    
    // 생성자
    public StarRatingDTO() {
        
    }
    
    public StarRatingDTO(StarRatingDTO s) {
        id = s.id;
        movieId = s.movieId;
        writer = s.writer;
        rating = s.rating;
        review = s.review;
        writtenDate = Calendar.getInstance();
        writtenDate.setTime(s.writtenDate.getTime());
    }
    
}
