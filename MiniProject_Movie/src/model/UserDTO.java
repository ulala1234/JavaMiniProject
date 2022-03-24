package model;

public class UserDTO {
    // 필드
    private int id;
    private String username;
    private String password;
    private String nickname;
    private int group;
        
    // 게터세터
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }
    
    // equals() 오버라이드
    public boolean equals(Object o) {
        if(o instanceof UserDTO) {
            UserDTO u = (UserDTO) o;
            return id == u.id;
        }
        return false;
    }
    
    // 생성자
    public UserDTO() {
        
    }
    
    public UserDTO(UserDTO u) {
        id = u.id;
        username = u.username;
        password = u.password;
        nickname = u.nickname;
        group = u.group;
    }
    
    
    
}
