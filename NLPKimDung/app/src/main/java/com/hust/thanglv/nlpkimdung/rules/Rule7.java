package com.hust.thanglv.nlpkimdung.rules;

import java.util.ArrayList;
import java.util.List;

public class Rule7 extends Rule {
    /*
	 * Check nếu có 2 nguyên âm giống hệt nhau đứng cạnh nhau (trừ oo)
	 */

    @Override
    public boolean checkInvalidate(String x) {
        String consolnant = "qrtpsdghklxcvbnmđ";
        List<Integer> list = new ArrayList<>(); // list chi so nguyen am
        x = x.toLowerCase();
        for (int i = 0; i < x.length(); i++) {
            if (consolnant.contains("" + x.charAt(i))) {

            } else {
                list.add(i);
            }
        }
        if (x.length() - list.size() >= 2) { // co nhieu hon 1 nguyen am
            for (int i = 0; i < list.size(); i++) {
                for (int j = 1; j < list.size(); j++) {
                    if (list.get(j) - list.get(i) == 1) {
                        if ((x.charAt(list.get(j)) == x.charAt(list.get(i))) && (x.charAt(list.get(j)) != 'o')) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }
}
