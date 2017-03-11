package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by LeHuyen on 11/30/2016.
 */

public class CheckH extends Rule {
    // Kiem tra cac tu la nguyen am
    public boolean checkNguyenAm(char c) {
        Boolean check = true;
        String phuAm = "qrtpsdđghklxcvbnm";
        if (phuAm.contains(c + "")) {
            check = false;
        }
        return check;
    }

    @Override
    public boolean checkInvalidate(String x) {
        boolean check = true;
        x = x.toLowerCase();
        int length = x.length();
        if (!x.contains('h' + "")) {
            check = false;
        }

        if (length >= 2) {
            if (x.charAt(x.length() - 1) == 'h') {
                if (x.charAt(x.length() - 2) == 'n' || x.charAt(x.length() - 2) == 'c') {
                    check = false;
                }
            }

            if (x.charAt(0) == 'h') {
                if (checkNguyenAm(x.charAt(1))) {
                    check = false;
                }
            }

            if (!(x.charAt(0) == 'h') && !(x.charAt(x.length() - 1) == 'h')) {
                if (x.charAt(1) == 'h') {
                    if (x.charAt(0) == 'k' || x.charAt(0) == 'c' || x.charAt(0) == 'n'
                            || x.charAt(0) == 'g' || x.charAt(0) == 't' || x.charAt(0) == 'p') {
                        check = false;
                    }
                }
                if (x.length() >= 3 && x.charAt(2) == 'h') {
                    if (x.charAt(1) == 'g') {
                        if (x.charAt(0) == 'n') {
                            check = false;
                        }
                    }
                }
            }

        }
        return check;
    }
}
