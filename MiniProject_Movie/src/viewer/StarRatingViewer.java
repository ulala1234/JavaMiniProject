package viewer;
// 평점 등급별로 따로 보이게 하는거 해결 못했어요.

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import util.ScannerUtil;
import controller.StarRatingController;
import model.StarRatingDTO;
import model.UserDTO;

public class StarRatingViewer {
    // 시간 상수
    private final String DATE_FORMAT = "yy-MM-dd H:m:s";   
    
//    // 회원 등급 일반 상수
//    private final int GEN_CODE = 1;
//    // 회원 등급 평론가 상수
//    private final int CRI_CODE = 2;
    
    // 데이터 입력 필드
    private Scanner scanner;
    // ArrayList 객체 간접 접근 필드
    private StarRatingController starRatingController;
//    // User의 로그인 필드
//    private UserDTO logIn;
    // UserViewer 필드
    private UserViewer userViewer;
    
    // 생성자 사용하여 필드 초기화
    public StarRatingViewer() {
        starRatingController = new StarRatingController();
        scanner = new Scanner(System.in);
    }    
    
    // UserViewer을 파라미터로 받아 값을 저장 setUserViewer()
    public void setUserViewer(UserViewer userViewer) {
        this.userViewer = userViewer;
    }
    
    // 파라미터에 영화 목록이 있다면 댓글 목록 출력 printList()
    public void printList(int movieId) {
        ArrayList<StarRatingDTO> list = starRatingController.selectAll(movieId);
        
        if(list.isEmpty()) {
            System.out.println("\n아직 등록된 리뷰가 존재하지 않습니다.\n");
        } else {
            for(StarRatingDTO s : list) {
                printOne(s);
            }
        }
    }
    
    // StarRatingDTO를 파라미터로 받아 세부 출력 printOne()
    private void printOne(StarRatingDTO s) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        System.out.printf("평점: %d점 평론: %s - by ", s.getRating(), s.getReview());
        userViewer.printNickname(s.getWriter());
        System.out.printf(" at %s\n", sdf.format(s.getWrittenDate().getTime()));
        
//        String message;
//        message = "1. 일반 관람객 평점 2. 전문가 평점 3. 목록으로 가기";
//        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
//        if(userChoice == 1) {
//            while(logIn.getGroup() == 1) {
//            System.out.printf("평점: %d점 평론: %s - by ", s.getRating(), s.getReview());
//            userViewer.printNickname(s.getWriter());
//            System.out.printf(" at %s\n", sdf.format(s.getWrittenDate().getTime()));
//            }
//        } else if(userChoice == 2) {
//            
//        } else if(userChoice == 3) {
//            
//        }
    }
    
    // 작성자와 영화 번호를 파라미터로 받아 새 평점과 평론 작성 writeReview()
    public void writeReview(int writer, int movieId) {
        StarRatingDTO s = new StarRatingDTO();
        s.setWriter(writer);
        s.setMovieId(movieId);
        
        String message = "평점을 입력해주세요.";
        s.setRating(ScannerUtil.nextInt(scanner, message, 1, 5));
        
        message = "평론을 입력해주세요.";
        s.setReview(ScannerUtil.nextLine(scanner, message));
        
        starRatingController.add(s);
    }
    
    // 작성자를 파라미터로 받아 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        starRatingController.deleteByWriter(writer);
    }

}
