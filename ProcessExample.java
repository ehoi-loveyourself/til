import java.io.IOException;

public class ProcessExample {
    public static void main(String[] args) {
        try {
            // macOS 에서 텍스트 편집기 실행
            Process notepad = new ProcessBuilder("open", "-a", "TextEdit").start();
            // macOS 에서 계산기 실행
            Process calc = new ProcessBuilder("open", "-a", "Calculator").start();

            System.out.println("두 개의 프로세스 실행 완료!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}