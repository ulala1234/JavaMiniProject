package controller;

import java.util.ArrayList;

import model.ScreenDTO;

public class ScreenController {
    // 데이터베이스 대신할 ArrayList 필드
    private ArrayList<ScreenDTO> list;
    // 다음 입력할 평점 번호를 저장할 nextId 필드
    private int nextId;

    // 필드를 초기화 할 생성자
    public ScreenController() {
        list = new ArrayList<>();
        nextId = 1;

        for (int i = 1; i <= 4; i++) {
            ScreenDTO s = new ScreenDTO();
            s.setMovieId(i);
            s.setMovieName("영화" + i);
            s.setWriter(3);
            s.setTheaterId(i);
            s.setTime("15:30");

            add(s);
        }
    }

    // 파라미터로 들어온 ScreenDTO 객체에 상영정보 번호 추가 후 list에 추가 add()
    public void add(ScreenDTO s) {
        s.setId(nextId++);

        list.add(s);
    }

    // 상영 번호 목록을 깊은 복사 후에 리턴 selectAll()
    public ArrayList<ScreenDTO> selectAll() {
        ArrayList<ScreenDTO> temp = new ArrayList<>();

        for (ScreenDTO s : list) {
            temp.add(new ScreenDTO(s));
        }

        return temp;

    }

    // 상영정보 번호를 파라미터로 받아 ScreenDTO에 일치 여부 확인 후 리턴 selectOne()
    public ScreenDTO selectOne(int id) {
        for (ScreenDTO s : list) {
            if (s.getId() == id) {
                return new ScreenDTO(s);
            }
        }

        return null;
    }

    // 파라미터로 들어온 ScreenDTO 객체를 원본의 해당 객체와 교체 update()
    public void update(ScreenDTO s) {
        list.set(list.indexOf(s), s);
    }

    // 극장 번호를 파라미터로 받아 TheaterDTO에 일치 여부 확인 후 삭제 delete()
    public void delete(int id) {
        ScreenDTO s = new ScreenDTO();
        s.setId(id);

        list.remove(s);
    }

    // 작성자를 파라미터로 받아 ScreenDTO에 일치 여부 확인 후 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        for (int i = 0; i < list.size(); i++) {
            ScreenDTO s = list.get(i);
            if (s.getWriter() == writer) {
                list.remove(i);
                i = -1;
            }
        }
    }
}
