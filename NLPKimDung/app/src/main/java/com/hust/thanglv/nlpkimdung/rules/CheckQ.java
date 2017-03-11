package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by LeHuyen on 11/30/2016.
 */

public class CheckQ extends Rule {

    @Override
    public boolean checkInvalidate(String x) {
        boolean check = false;
        if (x.length() >= 1 && x.charAt(0) == 'q') {
            if (x.charAt(1) != 'u') {
                check = true;
            }
        }

        return check;
    }
}
