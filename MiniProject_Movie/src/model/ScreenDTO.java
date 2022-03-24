package model;

public class ScreenDTO {
    // 필드
    private int id;
    private int movieId;
    private String movieName;
    private int theaterId;
    private int writer;
    private String time;
    
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

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }
    
    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    // equals() 오버라이드
    public boolean equals(Object o) {
        if(o instanceof ScreenDTO) {
            ScreenDTO s = (ScreenDTO) o;
            return id == s.id;
        }
        return false;
    }
    
    // 생성자
    public ScreenDTO() {
        
    }
    
    public ScreenDTO(ScreenDTO s) {
        id = s.id;
        movieId = s.movieId;
        movieName = s.movieName;
        theaterId = s.theaterId;
        writer = s.writer;
        time = s.time;
    }
    
    
}
