package controller;

import java.util.ArrayList;
import java.util.Calendar;

import model.ListDTO;

public class ListController {
    // 데이터베이스 대신할 ArrayList 필드
    private ArrayList<ListDTO> list;
    // 다음 입력할 영화 번호를 저장할 nextId 필드
    private int nextId; 
    
    // 필드를 초기화 할 생성자
    public ListController() {
        list = new ArrayList<>();
        nextId = 1;
        
        for(int i = 1; i <= 4; i++) {
            ListDTO l = new ListDTO();
            l.setTitle("영화" + i);
            l.setWriter(3);
            l.setContent("줄거리" + i);
            l.setGrade(i);
            add(l);            
        }
    }
    
    // 파라미터로 들어온 ListDTO 객체에 영화 번호 추가 후 list에 추가 add()
    public void add(ListDTO l) {
        l.setId(nextId++);
        l.setWrittenDate(Calendar.getInstance());
        l.setUpdatedDate(Calendar.getInstance());
        
        list.add(l);
    }
    
    // 영화 번호 목록을 깊은 복사 후에 리턴 selectAll()
    public ArrayList<ListDTO> selectAll() {
        ArrayList<ListDTO> temp = new ArrayList<>();
        for(ListDTO l : list) {
            temp.add(new ListDTO(l));
        }
        
        return temp;
    }
    
    // 영화 번호를 파라미터로 받아 ListDTO에 일치 여부 확인 후 리턴 selectOne()
    public ListDTO selectOne(int id) {
        for(ListDTO l : list) {
            if(l.getId() == id) {
                return new ListDTO(l);
            }
        }
        
        return null;
    }
    
    // 파라미터로 들어온 ListDTO 객체를 원본의 해당 객체와 교체 update()
    public void update(ListDTO l) {
        l.setUpdatedDate(Calendar.getInstance());
        
        list.set(list.indexOf(l), l);
    }
    
    // 영화 번호를 파라미터로 받아 ListDTO에 일치 여부 확인 후 삭제 delete()
    public void delete(int id) {
        ListDTO l = new ListDTO();
        l.setId(id);
        
        list.remove(l);
    }
    
    // 작성자를 파라미터로 받아 ListDTO에 일치 여부 확인 후 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        for(int i = 0; i < list.size(); i++) {
            ListDTO l = list.get(i);
            
            if(l.getWriter() == writer) {
                list.remove(i);
                i = -1;
            }
        }
    }
    
    

}
