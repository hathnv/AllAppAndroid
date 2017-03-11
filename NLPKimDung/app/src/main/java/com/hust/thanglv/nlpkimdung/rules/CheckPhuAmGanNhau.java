package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by LeHuyen on 11/30/2016.
 */

public class CheckPhuAmGanNhau extends Rule{

    @Override
    public boolean checkInvalidate(String x) {
        boolean check = false;
        String consonats = "qrtpsdđghklxcvbnm";
        for(int i = 0; i < x.length() - 1; i ++){
                if(consonats.contains(x.charAt(i)+"") && consonats.contains(x.charAt(i+1) + "")){
                    if(x.charAt(i) == x.charAt(i+1)){
                        check = true;
                        break;
                    }
                }
            }

        return check;
    }
}
