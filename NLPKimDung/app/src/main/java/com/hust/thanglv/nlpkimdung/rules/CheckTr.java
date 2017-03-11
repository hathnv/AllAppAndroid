package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by LeHuyen on 11/30/2016.
 */

public class CheckTr extends Rule {

    @Override
    public boolean checkInvalidate(String x) {
        boolean check = false;
        for(int i = 0; i < x.length() - 3; i++){
            if(x.charAt(i) == 't'){
                if(x.charAt(i + 1) == 'r'){

                    if(x.charAt(i + 2) == 'o'
                            && (x.charAt(i + 3) == 'a'
                            || x.charAt(i + 3) == 'ă'
                            || x.charAt(i + 3) == 'o')
                            || x.charAt(i + 2) == 'u'
                            && (x.charAt(i + 2) == 'ê')){
                        check = true;
                    }

                }
            }
        }
        return check;
    }
}
