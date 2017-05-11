package com.hust.thanglv.nlpkimdung.rules;


public class CheckT extends Rule {

    @Override
    public boolean checkInvalidate(String x) {
        boolean check = false;
        int length = x.length();
        if (length == 0) check = true;
        if (!x.contains('t' + "")) {
            check = false;
        }
        if (length >= 2) {
            if (x.charAt(0) == 't') {
                if (checkPhuAm(x.charAt(1))) {
                    if (x.charAt(1) == 'h' || x.charAt(1) == 'r') {
                        check = false;
                    } else {
                        check = true;
                    }
                }
            }
        }
        return check;
    }

    public boolean checkPhuAm(char c) {
        boolean check = false;
        String phuAm = "qrtpsdđghklxcvbnm";
        if (phuAm.contains(c + "")) {
            check = true;
        }
        return check;
    }

    public boolean checkNguyenAm(char c) {
        boolean check = true;
        String phuAm = "qrtpsdđghklxcvbnm";
        if (phuAm.contains(c + "")) {
            check = false;
        }
        return check;
    }


}
