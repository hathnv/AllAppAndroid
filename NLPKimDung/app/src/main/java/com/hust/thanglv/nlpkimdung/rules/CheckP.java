package com.hust.thanglv.nlpkimdung.rules;

public class CheckP extends Rule {
    @Override
    public boolean checkInvalidate(String x) {
        boolean check = true;
        String nguyenAm = "áắấạặậúụíịóọớợốộéẹếệ";
        int length = x.length();
        if(!x.contains('p'+ "")){
            check = false;
        }
        if(length - 2 >= 0){
            if( x.charAt(x.length() - 1) == 'p'){
                if (nguyenAm.contains(x.charAt(x.length() - 2) + "")) {
                    check = false;
                } else {
                    check = true;
                }
            }else{
                if(x.charAt(0) == 'p'){
                    if (x.charAt(1) == 'h') {
                        check = false;
                    }
                }
            }

        }
        return check;
    }
}
