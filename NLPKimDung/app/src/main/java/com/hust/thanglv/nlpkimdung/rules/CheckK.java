package com.hust.thanglv.nlpkimdung.rules;

public class CheckK extends Rule {
    @Override
    public boolean checkInvalidate(String x) {
        boolean check = false;
        int length = x.length();
        if (length == 0) check = true;
        String str = "eéẻẽẹêếểễệểiíịĩỉyýỷỹỵìỳềè";
        if(!x.contains('k' + "")) check = false;
        if (length >= 2) {
            if (x.charAt(0) == 'k') {
                if(checkPhuAm(x.charAt(1))){
                    if(x.charAt(1) == 'h'){
                        check = false;
                    }else{
                        check = true;
                    }
                }
                if (checkNguyenAm(x.charAt(1))) {
                    if(!str.contains(x.charAt(1) + "")){
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
