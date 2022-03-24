package viewer;

import java.util.Scanner;
import java.util.ArrayList;

import util.ScannerUtil;

import model.UserDTO;
import controller.UserController;

public class UserViewer {
    // 아이디 X는 입력 안되게 하는 상수
    private final String OPTION_GO_BACK = "X";
    
    // 회원 등급 상수
    private final int G_CODE1 = 1;
    private final int G_CODE2 = 2;
    private final int G_CODE3 = 3;
    
    // 회원 등급 입력 상수
    private final String G_INPUT = "1. 일반 관람객 2. 전문 평론가 3. 관리자";
    
    // 데이터 입력 필드
    private Scanner scanner;
    // ArrayList 객체 간접적 접근 필드
    private UserController userController;
    // logIn 필드
    private UserDTO logIn;
    // ListViewer 필드
    private ListViewer listViewer;
    // StarRatingViewer 필드
    private StarRatingViewer starRatingViewer;
    // TheaterViewer 필드
    private TheaterViewer theaterViewer;
    // ScreenViewer 필드
    private ScreenViewer screenViewer;
    
    
    // 생성자 사용하여 필드 초기화
    public UserViewer() {
        userController = new UserController();
        scanner = new Scanner(System.in);
        listViewer = new ListViewer();
        starRatingViewer = new StarRatingViewer();
        theaterViewer = new TheaterViewer();
        screenViewer = new ScreenViewer();
        listViewer.setStarRatingViewer(starRatingViewer);
        listViewer.setUserViewer(this);
        theaterViewer.setUserViewer(this);
        screenViewer.setUserViewer(this);
    }
    
    // 인덱스 화면 showIndex()
    public void showIndex() {
        String message = "1. 로그인 2. 회원가입 3. 종료";
        while(true) {
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            if(userChoice == 1) {
                logIn();
                
                if(logIn != null) {
                    showMenu();
                }
            } else if(userChoice == 2) {
                register();
            } else if(userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
    }
    
    // 회원가입 register()
    private void register() {
        String message;
        
        message = "사용하실 아이디나 뒤로 가시려면 X를 입력해주세요.";
        String username = ScannerUtil.nextLine(scanner, message);        
        
        while(userController.validateUsername(username)) {
            System.out.println("사용하실 수 없는 아이디입니다.");
            username = ScannerUtil.nextLine(scanner, message);
            
            if(username.equalsIgnoreCase(OPTION_GO_BACK)) {
                break;
            }
        }
        
        if(!username.equalsIgnoreCase(OPTION_GO_BACK)) {
            message = "사용하실 비밀번호를 입력해주세요.";
            String password = ScannerUtil.nextLine(scanner, message);
            
            message = "사용하실 닉네임을 입력해주세요.";
            String nickname = ScannerUtil.nextLine(scanner, message);
            
            message = "회원의 등급을 입력해주세요.(" + G_INPUT + ")";
            int group = ScannerUtil.nextInt(scanner, message, G_CODE1, G_CODE3);
            
            UserDTO u = new UserDTO();
            u.setUsername(username);
            u.setPassword(password);
            u.setNickname(nickname);
            u.setGroup(group);
            
            userController.add(u);
        }
    }
    
    // 로그인 화면 logIn()
    public void logIn() {
        String message;
        
        message = "아이디를 입력해주세요.";
        String username = ScannerUtil.nextLine(scanner, message);
        
        message = "비밀번호를 입력해주세요.";
        String password = ScannerUtil.nextLine(scanner, message);
        
        while(userController.auth(username, password) == null) {
            System.out.println("잘못 입력하셨습니다.");
            
            message = "아이디를 입력하시거나 뒤로 가시려면 X를 입력해주세요.";
            username = ScannerUtil.nextLine(scanner, message);
            
            if(username.equalsIgnoreCase(OPTION_GO_BACK)) {
                break;
            }
            
            message = "비밀번호를 입력해주세요.";
            password = ScannerUtil.nextLine(scanner, message);
        }
        
        logIn = userController.auth(username, password);
        
    }
    
    // 메뉴 화면 showMenu()
    private void showMenu() {
        String message = "1. 영화 목록 이동 2. 극장 목록 이동 3. 상영 정보 이동 4. 회원 정보 보기 5. 로그아웃";
        while(logIn != null) {
            
            listViewer.setLogIn(logIn);
            theaterViewer.setLogIn(logIn);
            screenViewer.setLogIn(logIn);
            
            int userChoice = ScannerUtil.nextInt(scanner, message);
            
            if(userChoice == 1) {
                listViewer.showMenu();
            } else if(userChoice == 2) {
                theaterViewer.showMenu();
            } else if(userChoice == 3) {
                screenViewer.showMenu();
            } else if(userChoice == 4) {
                printOne(logIn.getId());
            } else if(userChoice == 5) {
                System.out.println("로그아웃 되셨습니다.");
                logIn = null;
                listViewer.setLogIn(null);
                theaterViewer.setLogIn(null);
                screenViewer.setLogIn(null);
            }
        }
    }
    
    // id를 파라미터로 받는 회원 정보 화면 printOne()
    private void printOne(int id) {
        UserDTO u = userController.selectOne(id);
        System.out.println(u.getNickname() + "회원님의 정보");
        System.out.println("아이디: " + u.getUsername());
        System.out.println("닉네임: " + u.getNickname());
        System.out.printf("등급: %s\n", covertGroupCode(u.getGroup()));
        
        String message = "1. 회원 수정 2. 회원 탈퇴 3. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(scanner, message);
        
        if(userChoice == 1) {
            update(id);
        } else if(userChoice == 2) {
            delete(id);
        }
     }
    
    // id를 파라미터로 받는 회원 수정 update()
    private void update(int id) {
        String message;
        UserDTO u = userController.selectOne(id);
        
        message = "수정할 닉네임을 입력해주세요.";
        u.setNickname(ScannerUtil.nextLine(scanner, message));
        
        message = "수정할 회원 등급을 입력해주세요.";
        u.setGroup(ScannerUtil.nextInt(scanner, message));
        
        message = "수정할 비밀번호를 입력해주세요.";
        u.setPassword(ScannerUtil.nextLine(scanner, message));
        
        message = "기존 비밀번호를 다시 입력해주세요.";
        String oldPassword = ScannerUtil.nextLine(scanner, message);
        
        if(logIn.getPassword().equals(oldPassword)) {
            userController.update(u);
        }
        
        printOne(id);
    }
    
    // id를 파라미터로 받는 회원 탈퇴 delete()
    private void delete(int id) {
        String message = "정말로 탈퇴하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            userController.delete(id);
            listViewer.deleteByWriter(id);
            starRatingViewer.deleteByWriter(id);
            theaterViewer.deleteByWriter(id);
            screenViewer.deleteByWriter(id);
            logIn = null;
        } else {
            printOne(id);
        }
    }
    
    // 등급 코드 파라미터로 받아 등급명으로 리턴하는 covertGroupCode()
    private String covertGroupCode(int groupCode) {
        if(groupCode == G_CODE1) {
            return "일반 관람객";
        } else if(groupCode == G_CODE2) {
            return "전문 평론가";
        } else if(groupCode == G_CODE3) {
            return "관리자";
        }
        
        return null;
    }
    
    // id를 파라미터로 받아 닉네임을 출력하는 printNickname()
    public void printNickname(int id) {
        UserDTO u = userController.selectOne(id);
        System.out.print(u.getNickname());
    }
    
}
