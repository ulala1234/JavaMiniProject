package controller;

import java.util.ArrayList;

import model.UserDTO;

public class UserController {
    // 아이디 X는 입력 안되게 하는 상수
    private final String OPTION_GO_BACK = "X";
    
    // 데이터베이스 대신할 ArrayList 필드
    private ArrayList<UserDTO> list;
    // 다음 입력할 회원 번호를 저장할 int 필드
    private int nextId;
    
    // 필드를 초기화 할 생성자
    public UserController() {
        list = new ArrayList<>();
        nextId = 1;
        
        for(int i = 1; i <= 3; i++) {
            UserDTO u = new UserDTO();
            u.setUsername("u" + i);
            u.setPassword("1");
            u.setNickname("회원" + i);
            u.setGroup(i);
            add(u);
        }
        
        }
    
    // 파라미터로 들어온 UserDTO 객체에 회원번호 추가 후 list에 추가 add()
    public void add(UserDTO u) {
        u.setId(nextId++);
        
        list.add(u);
    }
    
    // 회원 번호 목록을 깊은 복사 후에 리턴 selectAll()
    public ArrayList<UserDTO> selectAll() {
        ArrayList<UserDTO> temp = new ArrayList<>();
        for(UserDTO u : list) {
            temp.add(new UserDTO(u));
        }
        
        return temp;
    }
    
    // 회원 번호를 파라미터로 받아 UserDTO에 일치 여부 확인 후 리턴 selectOne()
    public UserDTO selectOne(int id) {
        for(UserDTO u : list) {
            if(u.getId() == id) {
                return new UserDTO(u);
            }
        }
        
        return null;
    }
    
    // 파라미터로 들어온 UserDTO 객체를 원본의 해당 객체와 교체 update()
    public void update(UserDTO u) {
        list.set(list.indexOf(u), u);
    }
    
    // 파라미터로 들어온 회원 번호와 일치하는 UserDTO 객체를 list에서 삭제 delete()
    public void delete(int id) {
        UserDTO u = new UserDTO();
        u.setId(id);
        list.remove(u);
    }
    
    // 파라미터로 들어온 아이디 중복과 X 입력 방지 검증 validateUsername()
    public boolean validateUsername(String username) {
        if(username.equalsIgnoreCase(OPTION_GO_BACK)) {
            return true;
        }
        
        for(UserDTO u : list) {
            if(u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
    
    // 파라미터로 들어온 아이디와 비밀번호를 받아 UserDTO에 일치 여부 확인 auth()
    public UserDTO auth(String username, String password) {
        for(UserDTO u : list) {
            if(u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return new UserDTO(u);
            }
        }
        return null;
    }
    
}
