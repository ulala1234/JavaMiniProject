package viewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import util.ScannerUtil;

import model.ListDTO;
import controller.ListController;
import model.UserDTO;

public class ListViewer {
    // 시간 상수
    private final String DATE_FORMAT = "yy-MM-dd H:m:s";
    
    // 회원 등급 관리자 상수
    private final int MANAGE_CODE = 3;
    
    // 영화 등급 상수
    private final int G_CODE1 = 1;
    private final int G_CODE2 = 2;
    private final int G_CODE3 = 3;
    private final int G_CODE4 = 4;
    
    // 영화 등급 입력 상수
    private final String G_INPUT = "1. 7세 이상 관람가 2. 12세 이상 관람가 3. 15세 이상 관람가 4. 18세 이상 관람가";
    
    // 데이터 입력 필드
    private Scanner scanner;
    // ArrayList 객체 간접적 접근 필드
    private ListController listController;
    // User의 로그인 필드
    private UserDTO logIn;
    // UserViewer 필드
    private UserViewer userViewer;
    // StarRatingViewer 필드
    private StarRatingViewer starRatingViewer;
    
    // 생성자 사용하여 필드 초기화
    public ListViewer() {
        scanner = new Scanner(System.in);
        listController = new ListController();
    }
    
    // UserDTO를 파라미터로 받아 로그인 상태 체크 setLogIn()
    public void setLogIn(UserDTO u) {
        if(u != null) {
            logIn = new UserDTO(u);
        } else {
            logIn = null;
        }
    }
    
    // UserViewer을 파라미터로 받아 값을 저장 setUserViewer()
    public void setUserViewer(UserViewer userViewer) {
        this.userViewer = userViewer;
        starRatingViewer.setUserViewer(userViewer);
    }
    
    // StarRatingViewer를 파라미터로 받아 값을 저장 setStarRatingViewer()
    public void setStarRatingViewer(StarRatingViewer starRatingViewer) {
        this.starRatingViewer = starRatingViewer;
    }
    
    
    // 메뉴 화면 showMenu()
    public void showMenu() {
        ListDTO l = new ListDTO();
        String message;
        
        if(logIn.getGroup() == MANAGE_CODE) {
            message = "1. 새 영화 등록 2. 영화 목록 보기 3. 메인 화면으로";
            while(true) {
                int userChoice = ScannerUtil.nextInt(scanner, message);
                
                if(userChoice == 1) {
                    writeMovie();
                } else if(userChoice == 2) {
                    printAll();
                } else if(userChoice == 3) {
                    System.out.println("사용해주셔서 감사합니다.");
                    break;
                }
            }
        
        } else {
            message = "1. 영화 목록 보기 2. 메인 화면으로";
            while(true) {
                int userChoice = ScannerUtil.nextInt(scanner, message);
                
                if(userChoice == 1) {
                    printAll();
                } else if(userChoice == 2) {
                    System.out.println("사용해주셔서 감사합니다.");
                    break;
                } 
        }
    }
    }

    // 새 영화 등록 writeMovie()
    private void writeMovie() {
        ListDTO l = new ListDTO();
        String message;
        
        message = "영화의 제목을 입력해주세요.";
        l.setTitle(ScannerUtil.nextLine(scanner, message));
        
        l.setWriter(logIn.getId());
        
        message = "영화의 줄거리를 입력해주세요.";
        l.setContent(ScannerUtil.nextLine(scanner, message));
        
        message = "영화의 등급을 입력해주세요.(" + G_INPUT + ")";
        l.setGrade(ScannerUtil.nextInt(scanner, message, G_CODE1, G_CODE4));
        
        listController.add(l);
    }
    
    // 영화 목록 printAll()
    private void printAll() {
        ArrayList<ListDTO> list = listController.selectAll();
        
        if(list.isEmpty()) {
            System.out.println("아직 등록된 영화가 존재하지 않습니다.");
        } else {
            for(ListDTO l : list) {
                System.out.printf("%d. %s\n", l.getId(), l.getTitle());
            }
            
            String message = "상세보기 할 영화의 번호나 뒤로 가시려면 0을 입력해주세요.";
            int userChoice = ScannerUtil.nextInt(scanner, message);
            
            while(userChoice != 0 && listController.selectOne(userChoice) == null) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(scanner, message);
            }
            
            if(userChoice != 0) {
                printOne(userChoice);
            }
        }
        
    }
    
    // id를 파라미터로 받는 영화 세부 목록 printOne()
    private void printOne(int id) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        ListDTO l = listController.selectOne(id);
        
        System.out.println("제목: " + l.getTitle());
        System.out.print("작성자: ");
        userViewer.printNickname(l.getWriter());
        System.out.println();
        System.out.println("작성일: " + sdf.format(l.getWrittenDate().getTime()));
        System.out.println("수정일: " + sdf.format(l.getUpdatedDate().getTime()));
        System.out.printf("등급: %s\n", covertGradeCode(l.getGrade()));
        System.out.println(l.getContent());
        
        starRatingViewer.printList(id);
        
        String message;
        
        int optionMin, optionMax;
        
        if(logIn.getGroup() == MANAGE_CODE) {
            message = "1. 수정 2. 삭제 3. 목록으로 가기";
            optionMin = 1;
            optionMax = 3;
            
            int userChoice = ScannerUtil.nextInt(scanner, message, optionMin, optionMax);
            if(userChoice == 1) {
                update(id);
            } else if(userChoice == 2) {
                delete(id);
            } else {
                printAll();
            }
        } else {
            message = "1. 영화 리뷰하기 2. 목록으로 가기";
            optionMin = 1;
            optionMax = 2;
            
            int userChoice = ScannerUtil.nextInt(scanner, message, optionMin, optionMax);
            
            if(userChoice == 1) {
                starRatingViewer.writeReview(logIn.getId(), id);
                printOne(id);
            } else if(userChoice == 2) {
                printAll();
            }
        }
    }
    
    // id를 파라미터로 받아 수정 update()
    private void update(int id) {
        ListDTO l = listController.selectOne(id);
        
        String message;
        
        message = "새로운 영화 제목을 입력해주세요.";
        l.setTitle(ScannerUtil.nextLine(scanner, message));
        
        message = "새로운 영화 내용을 입력해주세요.";
        l.setContent(ScannerUtil.nextLine(scanner, message));
        
        message = "새로운 영화 등급을 입력해주세요.";
        l.setGrade(ScannerUtil.nextInt(scanner, message));
        
        listController.update(l);
        
        printOne(id);
    }
    
    // id를 파라미터로 받아 삭제 delete()
    private void delete(int id) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            listController.delete(id);
            printAll();
        } else {
            printOne(id);
        }
    }
    
    // 등급 코드 파라미터로 받아 등급명으로 리턴하는 covertGradeCode()
    private String covertGradeCode(int gradeCode) {
        if(gradeCode == 1) {
            return "7세 이상 관람가";
        } else if(gradeCode == 2) {
            return "12세 이상 관람가";
        } else if(gradeCode == 3) {
            return "15세 이상 관람가";
        } else if(gradeCode == 4) {
            return "18세 이상 관람가";
        }
        
        return null;
    }
    
    // 작성자를 파라미터로 받아 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        listController.deleteByWriter(writer);
    }
    
    
    

}
