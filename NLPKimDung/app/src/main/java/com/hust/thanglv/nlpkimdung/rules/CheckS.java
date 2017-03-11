package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by LeHuyen on 11/30/2016.
 */

public class CheckS extends Rule {
    // Chu s khong dung truoc oa, oă,,oe,,uê,uâ ngoai tru soát, soạt, soạng,soạn,suất

    @Override
    public boolean checkInvalidate(String x) {
        boolean check = false;
        x = x.toLowerCase();
        if (x.equals("soát") || x.equals("soạt") || x.equals("soạng") || x.equals("suất") || x.equals("Soạn")
                || x.equals("song")) {
            return false;
        }
        if (x.length() - 2 >= 0) {
            for (int i = 0; i < x.length() - 2; i++) {
                if (x.charAt(i) == 's') {
                    if (x.charAt(i + 1) == 'o'
                            && (x.charAt(i + 2) == 'a'
                            || x.charAt(i + 2) == 'ă'
                            || x.charAt(i + 1) == 'o')
                            || x.charAt(i + 1) == 'u'
                            && (x.charAt(i + 2) == 'ê')
                            || x.charAt(i + 2) == 'â') {
                        check = true;
                    }
                }
            }
        }
        return check;
    }
}
