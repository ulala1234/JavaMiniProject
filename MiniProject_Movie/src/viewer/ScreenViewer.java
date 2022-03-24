package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import util.ScannerUtil;

import model.ScreenDTO;
import model.UserDTO;
import controller.ScreenController;

public class ScreenViewer {
    // 시간 상수
    private final String TIME_PATTERN = "^[0-9]{1,2}:[0-9]{1,2}$";

    // 회원 등급 관리자 상수
    private final int MANAGE_CODE = 3;

    // 데이터 입력 필드
    private Scanner scanner;
    // ArrayList 객체 간접 접근 필드
    private ScreenController screenController;
    // User의 로그인 필드
    private UserDTO logIn;
    // UserViewer 필드
    private UserViewer userViewer;

    // 생성자 사용하여 필드 초기화
    public ScreenViewer() {
        screenController = new ScreenController();
        scanner = new Scanner(System.in);
    }

    // UserDTO를 파라미터로 받아 로그인 상태 체크 setLogIn()
    public void setLogIn(UserDTO u) {
        if (u != null) {
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

        if (logIn.getGroup() == MANAGE_CODE) {
            message = "1. 새 상영 정보 등록 2. 상영 목록 보기 3. 메인 화면으로";

            while (true) {
                int userChoice = ScannerUtil.nextInt(scanner, message);

                if (userChoice == 1) {
                    writeScreen();
                } else if (userChoice == 2) {
                    printAll();
                } else if (userChoice == 3) {
                    System.out.println("사용해주셔서 감사합니다.");
                    break;
                }
            }
        } else {
            message = "1. 상영 목록 보기 2. 메인 화면으로";
            while (true) {
                int userChoice = ScannerUtil.nextInt(scanner, message);

                if (userChoice == 1) {
                    printAll();
                } else if (userChoice == 2) {
                    System.out.println("사용해주셔서 감사합니다.");
                    break;
                }
            }
        }
    }

    // 새 상영 정보 등록 writeScreen()
    private void writeScreen() {
        ScreenDTO s = new ScreenDTO();

        String message;

        message = "영화 번호를 입력해주세요";
        s.setMovieId(ScannerUtil.nextInt(scanner, message));

        message = "영화 제목을 입력해주세요.";
        s.setMovieName(ScannerUtil.nextLine(scanner, message));

        s.setWriter(logIn.getId());

        message = "상영 장소를 입력해주세요.";
        s.setTheaterId(ScannerUtil.nextInt(scanner, message));

        message = "상영 시간을 입력해주세요.";
        s.setTime(ScannerUtil.nextLine(scanner, message));

        while (!s.getTime().matches(TIME_PATTERN)) {
            System.out.println("잘못 입력하셨습니다.");
            message = "상영 시간을 입력해주세요.";
            s.setTime(ScannerUtil.nextLine(scanner, message));
        }

        screenController.add(s);

    }

    // 상영 목록 printAll()
    private void printAll() {
        ArrayList<ScreenDTO> list = screenController.selectAll();

        if (list.isEmpty()) {
            System.out.println("아직 등록된 상영정보가 존재하지 않습니다.");
        } else {
            for (ScreenDTO s : list) {
                System.out.printf("%d. %d-%s\n", s.getId(), s.getMovieId(), s.getMovieName());
            }

            String message = "상세보기할 상영 정보의 번호나 뒤로 가시려면 0을 입력해주세요.";
            int userChoice = ScannerUtil.nextInt(scanner, message);

            while (userChoice != 0 && screenController.selectOne(userChoice) == null) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(scanner, message);
            }

            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    // id를 파라미터로 받아 상영 정보 세부 출력 printOne()
    private void printOne(int id) {
        ScreenDTO s = screenController.selectOne(id);

        System.out.print(s.getMovieId() + "-");
        System.out.println("제목: " + s.getMovieName());
        System.out.print("작성자: ");
        userViewer.printNickname(s.getWriter());
        System.out.println();
        System.out.println("상영 장소: " + s.getTheaterId() + "관");
        System.out.println("상영 시간: " + s.getTime());

        String message;

        int optionMin, optionMax;

        if (logIn.getGroup() == MANAGE_CODE) {
            message = "1. 수정 2. 삭제 3. 목록으로 가기";
            optionMin = 1;
            optionMax = 3;

            int userChoice = ScannerUtil.nextInt(scanner, message, optionMin, optionMax);
            if (userChoice == 1) {
                update(id);
            } else if (userChoice == 2) {
                delete(id);
            } else if (userChoice == 3) {
                printAll();
            }
        } else {
            message = "0. 목록으로 가기";

            optionMin = 0;
            optionMax = 0;

            int userChoice = ScannerUtil.nextInt(scanner, message, optionMin, optionMax);

            if (userChoice == 0) {
                printAll();
            }
        }
    }

    // id를 파라미터로 받아 수정 update()
    private void update(int id) {
        ScreenDTO s = screenController.selectOne(id);
        String message;

        message = "새로운 영화 번호를 입력해주세요";
        s.setMovieId(ScannerUtil.nextInt(scanner, message));

        message = "새로운 영화 제목을 입력해주세요.";
        s.setMovieName(ScannerUtil.nextLine(scanner, message));

        message = "새로운 상영 장소를 입력해주세요.";
        s.setTheaterId(ScannerUtil.nextInt(scanner, message));

        message = "새로운 상영 시간을 입력해주세요.";
        s.setTime(ScannerUtil.nextLine(scanner, message));

        while (!s.getTime().matches(TIME_PATTERN)) {
            System.out.println("잘못 입력하셨습니다.");
            message = "상영 시간을 입력해주세요.";
            s.setTime(ScannerUtil.nextLine(scanner, message));
        }

        screenController.update(s);

        printOne(id);
    }

    // id를 파라미터로 받아 삭제 delete()
    private void delete(int id) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(scanner, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            screenController.delete(id);
            printAll();
        } else {
            printOne(id);
        }
    }

    // 작성자를 파라미터로 받아 삭제 deleteByWriter()
    public void deleteByWriter(int writer) {
        screenController.deleteByWriter(writer);
    }

}
