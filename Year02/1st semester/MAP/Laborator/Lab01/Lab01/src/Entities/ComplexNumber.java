package Entities;

public class ComplexNumber {
    int realPart;
    int imaginaryPart;

    public ComplexNumber(int realPart, int imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public ComplexNumber(String complexNumberAsString) {
        int realPart = 0;
        int signRealPart = 1;

        char[] ch = complexNumberAsString.toCharArray();

        if (ch[0] == '-') {
            signRealPart = -1;
            complexNumberAsString = complexNumberAsString.substring(1);
            ch = complexNumberAsString.toCharArray();
        }

        for (int i = 0; i<ch.length; ++i) {
            if ('0' <= ch[i] && ch[i] <= '9') {
                realPart = realPart * 10 + (ch[i] - '0');
            } else {
                complexNumberAsString = complexNumberAsString.substring(i);
                ch = complexNumberAsString.toCharArray();
                break;
            }
        }

        int imaginaryPart = 0;
        int signImaginaryPart = 1;

        if (ch[0] == '-') {
            signImaginaryPart = -1;
        }
        ch = complexNumberAsString.substring(1).toCharArray();

        for (char c : ch) {
            if ('0' <= c && c <= '9') {
                imaginaryPart = imaginaryPart * 10 + (c - '0');
            } else {
                break;
            }
        }

        this.realPart = realPart*signRealPart;
        this.imaginaryPart = imaginaryPart*signImaginaryPart;
    }

    public String getPrintForm() {
        String res;

        if (this.imaginaryPart >= 0) {
            res = Integer.toString(this.realPart) + '+' + Integer.toString(this.imaginaryPart) + "*i";
        } else {
            res = Integer.toString(this.realPart) + Integer.toString(this.imaginaryPart) + "*i";
        }

        return res;
    }

    public int getImaginaryPart() {
        return imaginaryPart;
    }

    public int getRealPart() {
        return realPart;
    }
}
