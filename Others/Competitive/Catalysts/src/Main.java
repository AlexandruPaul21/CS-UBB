import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n;
        String[] mat;
        Scanner scanner = new Scanner("level1/level1_1.in");
        n = scanner.nextInt();
        scanner.next();
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            String act = scanner.next();
            for (int j = 0; j < act.length(); ++j) {
                if (act.charAt(j) == 'C') {
                    ++ans;
                }
            }
        }
        System.out.println(ans);
    }
}