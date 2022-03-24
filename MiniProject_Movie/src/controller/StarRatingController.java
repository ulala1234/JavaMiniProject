package controller;

import java.util.ArrayList;
import java.util.Calendar;

import model.StarRatingDTO;

public class StarRatingController {
    // 데이터베이스 대신할 ArrayList 필드
    private ArrayList<StarRatingDTO> list;
    // 다음 입력할 평점 번호를 저장할 nextId 필드
    private int nextId;
    
    // 필드를 초기화 할 생성자
    public StarRatingController() {
        list = new ArrayList<>();
        nextId = 1;
        
        for (int i = 1; i <= 4; i++) {
            StarRatingDTO s = new StarRatingDTO();
            s.setMovieId(2);
            s.setWriter(1);
            s.setRating(4);
            s.setReview("평론" + i);
            
            add(s);
        }
    }
    
    // 파라미터로 들어온 StarRatingDTO 객체에 평점 번호 추가 후 list에 추가 add()
    public void add(StarRatingDTO s) {
        s.setId(nextId++);
        s.setWrittenDate(Calendar.getInstance());
        
        list.add(s);
    }
    
    // 파라미터로 받는 영화 목록이 있다면 평점 번호 목록을 깊은 복사 후에 리턴 selectAll()
    public ArrayList<StarRatingDTO> selectAll(int movieId) {
        ArrayList<StarRatingDTO> temp = new ArrayList<>();
        
        for(StarRatingDTO s : list) {
            if(s.getMovieId() == movieId) {
                temp.add(new StarRatingDTO(s));
            }
        }
        return temp;
    }
    
    // 작성자를 파라미터로 받아 StarRatingDTO에 일치 여부 확인 후 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        for(int i = 0; i < list.size(); i++) {
            StarRatingDTO s = list.get(i);
            if(s.getWriter() == writer) {
                list.remove(i);
                i = -1;
            }
        }
    }
}
