package util;

import java.util.Scanner;

public class ScannerUtil {
    // 1. 사용자가 출력하고자 하는 메시지를 양식에 맞추어 출력 printMessage()
    public static void printMessage(String message) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(message);
        System.out.println("-------------------------------------------------------------");
        System.out.print("> ");
    }
    
    // 2. int 값을 입력 받아 리턴해주고 메시지도 출력 + 숫자만 입력되게 nextInt()
    public static int nextInt(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        
        while(!temp.matches("[0-9]+")) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }
        
        return Integer.parseInt(temp);
    }
    
    // 3. 특정 범위의 int 값만 입력을 받아 리턴해주고 메시지도 출력 nextInt()
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        int temp = nextInt(scanner, message);
        
        while(!(temp >= min && temp <= max)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextInt(scanner, message);
        }
        
        return temp;
    }
    
    // 4. String 값을 입력받아 리턴해주고 메시지도 출력 + Scanner 버그 자동 해결 nextLine()
    public static String nextLine(Scanner scanner, String message) {
        printMessage(message);
        String temp = scanner.nextLine();
        
        if(temp.isEmpty()) {
            temp = scanner.nextLine();
        }
        
        return temp;
    }
    
    // 5. double 값을 입력받아 리턴해주고 메시지도 출력 nextDouble()
    public static double nextDouble(Scanner scanner, String message) {
        printMessage(message);
        
        double temp = scanner.nextDouble();
        
        return temp;
    }
    
    // 6. 특정 범위의 double 값을 입력 받아 리턴해주고 메시지도 출력 (단, 초과와 미만으로 처리) nextDouble()
    public static double nextDouble(Scanner scanner, String message, double min, double max) {
        double temp = nextDouble(scanner, message);
        
        while(!(temp > min && temp < max)) {
            System.out.println("잘못 입력하셔습니다.");
            temp = nextDouble(scanner, message);
        }
        
        return temp;
    }
    
}
