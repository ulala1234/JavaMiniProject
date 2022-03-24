package model;
import java.util.Calendar;

public class ListDTO {
    // 필드
    private int id;
    private String title;
    private int writer;
    private String content;
    private int grade;
    private Calendar writtenDate;
    private Calendar updatedDate;
    
    // 게터세터
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getWriter() {
        return writer;
    }
    public void setWriter(int writer) {
        this.writer = writer;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public Calendar getWrittenDate() {
        return writtenDate;
    }
    public void setWrittenDate(Calendar writtenDate) {
        this.writtenDate = writtenDate;
    }
    public Calendar getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    // equals() 오버라이드
    public boolean equals(Object o) {
        if(o instanceof ListDTO) {
            ListDTO l = (ListDTO) o;
            return id == l.id;
        }
        
        return false;
    }
    
    // 생성자
    public ListDTO() {
        
    }
    
    public ListDTO(ListDTO l) {
        id = l.id;
        title = l.title;
        writer = l.writer;
        content = l.content;
        grade = l.grade;
        writtenDate = Calendar.getInstance();
        writtenDate.setTime(l.writtenDate.getTime());
        updatedDate = Calendar.getInstance();
        updatedDate.setTime(l.updatedDate.getTime());
    }
    
}
