package model;

public class TheaterDTO {
    // 필드
    private int id;
    private int writer;
    private String name;
    private String location;
    private String number;
    
    // 게터세터
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getWriter() {
        return writer;
    }
    public void setWriter(int writer) {
        this.writer = writer;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    
    // equals() 오버라이드
    public boolean equals(Object o) {
        if(o instanceof TheaterDTO) {
            TheaterDTO t = (TheaterDTO) o;
            return id == t.id;
        }
        return false;
    }
    
    // 생성자
    public TheaterDTO() {
        
    }
    
    public TheaterDTO(TheaterDTO t) {
        id = t.id;
        writer = t.writer;
        name = t.name;
        location = t.location;
        number = t.number;
    }
    
    
}
