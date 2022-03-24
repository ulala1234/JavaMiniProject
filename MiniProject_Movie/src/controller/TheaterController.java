package controller;

import java.util.ArrayList;

import model.TheaterDTO;

public class TheaterController {
    // 데이터베이스를 대신할 ArrayList 필드
    private ArrayList<TheaterDTO> list;
    // 다음에 입력할 극장 번호를 저장할 nextId 필드
    private int nextId;
    
    // 필드를 초기화할 생성자
    public TheaterController() {
        list = new ArrayList<>();
        nextId = 1;
        
        for(int i = 1; i <= 5; i++) {
            TheaterDTO t = new TheaterDTO();
            t.setName("극장" + i);
            t.setWriter(3);
            t.setLocation("위치" + i);
            t.setNumber("02-111-111" + i);
            add(t);
        }
    }
    
    // 파라미터로 들어온 TheaterDTO 객체에 영화 번호 추가 후 list에 추가 add()
    public void add(TheaterDTO t) {
        t.setId(nextId++);
        
        list.add(t);
    }
    
    // 극장 번호 목록을 깊은 복사 후에 리턴 selectAll()
    public ArrayList<TheaterDTO> selectAll() {
        ArrayList<TheaterDTO> temp = new ArrayList<>();
        for(TheaterDTO t : list) {
            temp.add(new TheaterDTO(t));
        }
        return temp;
    }
    
    // 극장 번호를 파라미터로 받아 TheaterDTO에 일치 여부 확인 후 리턴 selectOne()
    public TheaterDTO selectOne(int id) {
        for(TheaterDTO t : list) {
            if(t.getId() == id) {
                return new TheaterDTO(t);
            }
        }
        
        return null;
    }
    
    // 파라미터로 들어온 TheaterDTO 객체를 원본의 해당 객체와 교체 update()
    public void update(TheaterDTO t) {
        list.set(list.indexOf(t), t);
    }
    
    // 극장 번호를 파라미터로 받아 TheaterDTO에 일치 여부 확인 후 삭제 delete()
    public void delete(int id) {
        TheaterDTO t = new TheaterDTO();
        t.setId(id);
        
        list.remove(t);
    }
    
    // 작성자를 파라미터로 받아 TheaterDTO에 일치 여부 확인 후 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        for(int i = 0; i < list.size(); i++) {
            TheaterDTO t = list.get(i);
            if(t.getWriter() == writer) {
                list.remove(i);
                i = -1;
            }
        }
    }
}
