import Entities.ComplexNumber;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length <= 1) {
            System.out.println("Not enough arguments");
            return;
        }

        boolean good = true;
        String op = "n";
        for (int i = 0; i <args.length; ++i) {
            if (i % 2 == 0 && args[i].length() < 2) {
                good = false;
            }
            if (i % 2 == 1) {
                if (args[i].length() != 1) {
                    good = false;
                } else {
                    if (Objects.equals(op, "n")) {
                        op = args[i];
                    }
                    if (!Objects.equals(op, args[i])) {
                        good = false;
                    }
                }
            }
        }

        if (good) {
            System.out.println("Da");
        } else {
            System.out.println("Nu");
        }
    }
}