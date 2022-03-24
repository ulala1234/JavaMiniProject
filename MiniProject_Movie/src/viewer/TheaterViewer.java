package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import util.ScannerUtil;

import model.TheaterDTO;
import controller.TheaterController;
import model.UserDTO;

public class TheaterViewer {
    // 전화번호 상수
    private final String NUM_PATTERN = "^0[1-6]{1,2}-[0-9]{3,4}-[0-9]{4}$";
    
    // 회원 등급 관리자 상수
    private final int MANAGE_CODE = 3;
    
    // 데이터 입력 필드
    private Scanner scanner;
    // ArrayList 객체 간접적 접근 필드
    private TheaterController theaterController;
    // User의 로그인 필드
    private UserDTO logIn;
    // UserViewer 필드
    private UserViewer userViewer;
    
    // 생성자 사용하여 필드 초기화
    public TheaterViewer() {
        theaterController = new TheaterController();
        scanner = new Scanner(System.in);
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
    }
    
    // 메뉴화면 showMenu()
    public void showMenu() {
        String message;
        
        if(logIn.getGroup() == MANAGE_CODE) {
            message = "1. 새 극장 등록 2. 극장 목록 보기 3. 메인 화면으로";
            while(true) {
                int userChoice = ScannerUtil.nextInt(scanner, message);
                
                if(userChoice == 1) {
                    writeTheater();
                } else if(userChoice == 2) {
                    printAll();
                } else if(userChoice == 3) {
                    System.out.println("사용해주셔서 감사합니다.");
                    break;
                }
            }
        } else {
            message = "1. 극장 목록 보기 2. 메인 화면으로";
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
    
    // 새 극장 등록 writeTheater()
    private void writeTheater() {
        TheaterDTO t = new TheaterDTO();
        String message;
        
        message = "극장의 이름을 입력해주세요.";
        t.setName(ScannerUtil.nextLine(scanner, message));
        
        t.setWriter(logIn.getId());
        
        message = "극장의 위치를 입력해주세요.";
        t.setLocation(ScannerUtil.nextLine(scanner, message));
        
        message = "극장의 전화번호를 입력해주세요.";
        t.setNumber(ScannerUtil.nextLine(scanner, message));
        
        while(!t.getNumber().matches(NUM_PATTERN)) {
            System.out.println("잘못 입력하셨습니다.");
            message = "극장의 전화번호를 입력해주세요.";
            t.setNumber(ScannerUtil.nextLine(scanner, message));
        }
        
        theaterController.add(t);
    }
    
    // 극장 목록 printAll()
    private void printAll() {
        ArrayList<TheaterDTO> list = theaterController.selectAll();
        
        if(list.isEmpty()) {
            System.out.println("아직 등록된 극장이 존재하지 않습니다.");
        } else {
            for(TheaterDTO t : list) {
                System.out.printf("%d. %s\n", t.getId(), t.getName());            
            }
            
            String message = "상세보기할 극장의 번호나 뒤로 가시려면 0을 입력해주세요.";
            int userChoice = ScannerUtil.nextInt(scanner, message);
            
            while(userChoice != 0 && theaterController.selectOne(userChoice) == null) {
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
        TheaterDTO t = theaterController.selectOne(id);
        
        System.out.println("이름: " + t.getName());
        System.out.print("작성자 ");
        userViewer.printNickname(t.getWriter());
        System.out.println();
        System.out.println("위치: " + t.getLocation());
        System.out.println("전화번호: " + t.getNumber());
        
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
            message = "0. 목록으로 가기";
            
            optionMin = 0;
            optionMax = 0;
            
            int userChoice = ScannerUtil.nextInt(scanner, message, optionMin, optionMax);
            
            if(userChoice == 0) {
                printAll();
            }
        }
    }
    
    // id를 파라미터로 받아 수정 update()
    private void update(int id) {
        TheaterDTO t = theaterController.selectOne(id);
        
        String message;

        message = "새로운 극장 이름을 입력해주세요.";
        t.setName(ScannerUtil.nextLine(scanner, message));
        
        message = "새로운 극장 위치를 입력해주세요.";
        t.setLocation(ScannerUtil.nextLine(scanner, message));
        
        message = "새로운 전화번호를 입력해주세요.";
        t.setNumber(ScannerUtil.nextLine(scanner, message));
        
        while(!t.getNumber().matches(NUM_PATTERN)) {
            System.out.println("잘못 입력하셨습니다.");
            message = "극장의 전화번호를 입력해주세요.";
            t.setNumber(ScannerUtil.nextLine(scanner, message));
        }
        
        theaterController.update(t);
        
        printOne(id);
        
    }
    
    // id를 파라미터로 받아 삭제 delete()
    private void delete(int id) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            theaterController.delete(id);
            printAll();
        } else {
            printOne(id);
        }

    }
    
    // 작성자를 파라미터로 받아 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        theaterController.deleteByWriter(writer);
    }

}
